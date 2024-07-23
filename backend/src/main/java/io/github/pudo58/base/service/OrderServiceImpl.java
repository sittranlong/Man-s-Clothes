package io.github.pudo58.base.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.pudo58.base.entity.*;
import io.github.pudo58.base.repo.CartRepo;
import io.github.pudo58.base.repo.OrderRepo;
import io.github.pudo58.base.repo.VoucherRepo;
import io.github.pudo58.constant.OrderConst;
import io.github.pudo58.constant.PaymentConst;
import io.github.pudo58.constant.VoucherConst;
import io.github.pudo58.dto.CommonRequest;
import io.github.pudo58.dto.Contact;
import io.github.pudo58.dto.OrderActionRequest;
import io.github.pudo58.dto.OrderRequest;
import io.github.pudo58.record.AlertResponseRecord;
import io.github.pudo58.util.EmailSender;
import io.github.pudo58.util.Message;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;
    private final CartRepo cartRepo;
    private final VoucherRepo voucherRepo;
    private final Message message;
    private final RedisTemplate<String, Object> redisTemplate;
    private final EmailSender emailSender;

    @Value("${vietqr.url}")
    private String vietQRURL;
    @Value("${shipping.fee}")
    private int shippingFee;


    @Override
    public void saveContact(OrderRequest model) {
        Assert.notNull(User.getContext(), message.getMessage("authentication.required"));
        User user = User.getContext();
        Contact contact = new Contact();
        contact.setAddress(model.getAddress());
        contact.setPhone(model.getPhone());
        contact.setUser(user);
        contact.setCartIdList(model.getCartIdList());
        redisTemplate.opsForValue().set("contact:" + user.getId(), contact);
    }

    @Override
    @Transactional(rollbackFor = IllegalArgumentException.class)
    public ResponseEntity<?> createOrder(OrderRequest model) throws NoSuchAlgorithmException, IOException, InterruptedException, URISyntaxException {
        User user = User.getContext();
        Assert.notNull(user, message.getMessage("authentication.required"));
        List<Cart> cartList = this.cartRepo.findAllById(model.getCartIdList());
        if (cartList.isEmpty()) {
            return ResponseEntity.badRequest().body(new AlertResponseRecord(message.getMessage("cart.empty"), HttpStatus.BAD_REQUEST.value()));
        } else {
            List<OrderDetail> orderDetailList = OrderDetail.fromCartList(cartList);
            Order order = new Order();
            order.setUser(user);
            order.setAddress(model.getAddress());
            order.setPhone(model.getPhone());
            order.setNote(model.getNote());
            order.setName(model.getName());
            order.setPaymentMethod(model.getPaymentMethod());
            order.setTotal(cartList.stream().mapToInt(cart -> cart.getProductDetail().getProduct().getPrice() * cart.getQuantity()).sum());
            int shippingFee;
            if (PaymentConst.METHOD_AT_COUNTER.equals(model.getPaymentMethod())) {
                shippingFee = 0;
                order.setStatus(OrderConst.STATUS_COMPLETED);
                orderDetailList.forEach(orderDetail -> {
                    ProductDetail productDetail = orderDetail.getProductDetail();
                    productDetail.setQuantity(productDetail.getQuantity() - orderDetail.getQuantity());
                });
            } else {
                shippingFee = order.getTotal() > 1_000_000 ? 0 : this.shippingFee;
                order.setStatus(OrderConst.STATUS_PENDING);
            }
            order.setFinalTotal(order.getTotal());
            order.setShippingFee(shippingFee);
            Voucher voucher = voucherRepo.findByCode(model.getVoucherCode()).orElse(null);
            if (voucher != null) {
                if (VoucherConst.STATUS_INACTIVE.equals(voucher.getStatus())) {
                    return ResponseEntity.badRequest().body(new AlertResponseRecord(message.getMessage("voucher.invalid"), HttpStatus.BAD_REQUEST.value()));
                }
                // check startDate, endDate
                Date currentDate = new Date();
                if (currentDate.before(voucher.getStartDate()) || currentDate.after(voucher.getEndDate())) {
                    return ResponseEntity.badRequest().body(new AlertResponseRecord(message.getMessage("voucher.invalid"), HttpStatus.BAD_REQUEST.value()));
                }
                if (voucher.getMaxUsage() != null) {
                    if (voucher.getUsage() == null) {
                        voucher.setUsage(0);
                    }
                    if (voucher.getMaxUsage() != null && voucher.getUsage() >= voucher.getMaxUsage()) {
                        return ResponseEntity.badRequest().body(new AlertResponseRecord(message.getMessage("voucher.expired"), HttpStatus.BAD_REQUEST.value()));
                    }
                }
                if (voucher.getMinTotal() != null && order.getFinalTotal() < voucher.getMinTotal()) {
                    return ResponseEntity.badRequest().body(new AlertResponseRecord(message.getMessage("voucher.invalid"), HttpStatus.BAD_REQUEST.value()));
                }
                order.setVoucher(voucher);
                voucher.setUsage(voucher.getUsage() + 1);
                voucherRepo.save(voucher);

                // calculate discount
                int discount = voucher.getDiscountValue(order.getTotal());
                order.setDiscount(discount);
                order.setFinalTotal(order.getFinalTotal() - discount + shippingFee);
            } else {
                order.setFinalTotal(order.getFinalTotal() + shippingFee);
                order.setDiscount(0);
            }
            Order orderSaved = this.orderRepo.save(order);
            orderDetailList.forEach(orderDetail -> orderDetail.setOrder(orderSaved));
            orderSaved.setOrderDetails(orderDetailList);
            ResponseEntity<?> qrCode = this.getQrCode(orderSaved.getId());
            Map<String, Object> response = Map.of(
                    "total", order.getTotal(),
                    "shippingFee", shippingFee,
                    "finalTotal", order.getFinalTotal(),
                    "cartList", cartList,
                    "qrCode", Objects.nonNull(qrCode.getBody())
            );
            return ResponseEntity.ok(response);
        }
    }

    @Override
    public ResponseEntity<?> getOrderInfo(OrderRequest model) throws URISyntaxException, NoSuchAlgorithmException, IOException, InterruptedException {
        Assert.notNull(User.getContext(), message.getMessage("authentication.required"));
        List<Cart> cartList = this.cartRepo.findAllById(model.getCartIdList());
        if (cartList.isEmpty()) {
            return ResponseEntity.badRequest().body(new AlertResponseRecord(message.getMessage("cart.empty"), HttpStatus.BAD_REQUEST.value()));
        } else {
            int total = cartList.stream().mapToInt(cart -> cart.getProductDetail().getProduct().getPrice() * cart.getQuantity()).sum();
            int shippingFee;
            if (PaymentConst.METHOD_AT_COUNTER.equals(model.getPaymentMethod())) {
                shippingFee = 0;
            } else {
                shippingFee = total > 1_000_000 ? 0 : this.shippingFee;
            }
            int finalTotal = total + shippingFee;
            int voucherDiscount = 0;
            if (model.getVoucherCode() != null) {
                Voucher voucher = voucherRepo.findByCode(model.getVoucherCode()).orElse(null);
                if (voucher != null) {
                    if (VoucherConst.STATUS_INACTIVE.equals(voucher.getStatus())) {
                        return ResponseEntity.badRequest().body(new AlertResponseRecord(message.getMessage("voucher.invalid"), HttpStatus.BAD_REQUEST.value()));
                    }
                    // check startDate, endDate
                    Date currentDate = new Date();
                    if (currentDate.before(voucher.getStartDate()) || currentDate.after(voucher.getEndDate())) {
                        return ResponseEntity.badRequest().body(new AlertResponseRecord(message.getMessage("voucher.invalid"), HttpStatus.BAD_REQUEST.value()));
                    }
                    if (voucher.getMaxUsage() != null) {
                        if (voucher.getUsage() == null) {
                            voucher.setUsage(0);
                        }
                        if (voucher.getMaxUsage() != null && voucher.getUsage() >= voucher.getMaxUsage()) {
                            return ResponseEntity.badRequest().body(new AlertResponseRecord(message.getMessage("voucher.expired"), HttpStatus.BAD_REQUEST.value()));
                        }
                    }
                    if (voucher.getMinTotal() != null && finalTotal < voucher.getMinTotal()) {
                        return ResponseEntity.badRequest().body(new AlertResponseRecord(message.getMessage("voucher.invalid"), HttpStatus.BAD_REQUEST.value()));
                    }
                    finalTotal -= voucher.getDiscountValue(total);
                    voucherDiscount = voucher.getDiscountValue(total);
                } else {
                    return ResponseEntity.badRequest().body(new AlertResponseRecord(message.getMessage("voucher.invalid"), HttpStatus.BAD_REQUEST.value()));
                }
            }
            final String jsonPayload = "{\n" +
                    "  \"accountNo\": \"0968242106\",\n" +
                    "  \"accountName\": \"TRAN HOANG LONG\",\n" +
                    "  \"acqId\": 970422,\n" +
                    "  \"amount\": " + finalTotal + ",\n" +
                    "  \"addInfo\": \"" + User.getContext().getUsername() + "-" + model.getName() + "- Thanh toan don hang gia tri " + finalTotal + " VND\",\n" +
                    "  \"format\": \"text\",\n" +
                    "  \"template\": \"compact2\"\n" +
                    "}";
            HttpURLConnection connection = (HttpURLConnection) new URI(vietQRURL).toURL().openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            connection.getOutputStream().write(jsonPayload.getBytes());
            ObjectMapper objectMapper = new ObjectMapper();
            Map<?, ?> qrCodeResponse = objectMapper.readValue(connection.getInputStream(), Map.class);
            Map<String, Object> response = Map.of(
                    "total", total,
                    "shippingFee", shippingFee,
                    "finalTotal", finalTotal,
                    "cartList", cartList,
                    "voucherDiscount", voucherDiscount,
                    "qrCode", qrCodeResponse
            );
            return ResponseEntity.ok(response);
        }
    }

    @Override
    public ResponseEntity<?> getQrCode(UUID orderId) throws NoSuchAlgorithmException, IOException, InterruptedException, URISyntaxException {
        Assert.notNull(User.getContext(), message.getMessage("authentication.required"));
        Order order = orderRepo.getByIdAndStatusIn(orderId, List.of(OrderConst.STATUS_PENDING, OrderConst.STATUS_PROCESSING));
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        final String jsonPayload = "{\n" +
                "  \"accountNo\": \"0968242106\",\n" +
                "  \"accountName\": \"TRAN HOANG LONG\",\n" +
                "  \"acqId\": 970422,\n" +
                "  \"amount\": " + order.getFinalTotal() + ",\n" +
                "  \"addInfo\": \"" + User.getContext().getUsername() + "-" + order.getName() + "- Thanh toan don hang gia tri " + order.getFinalTotal() + " VND\",\n" +
                "  \"format\": \"text\",\n" +
                "  \"template\": \"compact2\"\n" +
                "}";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(vietQRURL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();
        // get data from vietqr
        HttpClient client = HttpClient.newBuilder()
                .sslContext(SSLContext.getDefault())
                .build();
        HttpResponse<?> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return ResponseEntity.ok(response.body());
    }

    @Override
    public ResponseEntity<?> cancelOrder(OrderRequest model) {
        Order order = orderRepo.getByIdAndStatusIn(model.getOrderId(), List.of(OrderConst.STATUS_PENDING, OrderConst.STATUS_PROCESSING));
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        order.setStatus(OrderConst.STATUS_CANCELLED);
        orderRepo.save(order);
        this.rollbackVoucher(order);
        return ResponseEntity.ok(new AlertResponseRecord(message.getMessage("order.cancel-success"), HttpStatus.OK.value()));
    }

    @Override
    public ResponseEntity<?> getOrder(UUID orderId) {
        Order order = orderRepo.findById(orderId).orElse(null);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(order);
    }

    @Override
    public ResponseEntity<?> approveOrder(UUID orderId) {
        Order order = orderRepo.getByIdAndStatusIn(orderId, List.of(OrderConst.STATUS_PENDING, OrderConst.STATUS_PROCESSING));
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        order.setStatus(OrderConst.STATUS_PROCESSING);
        order.getOrderDetails().forEach(orderDetail -> {
            ProductDetail productDetail = orderDetail.getProductDetail();
            productDetail.setQuantity(productDetail.getQuantity() - orderDetail.getQuantity());
        });

        orderRepo.save(order);
        return ResponseEntity.ok(new AlertResponseRecord(message.getMessage("order.approve-success"), HttpStatus.OK.value()));
    }

    @Override
    public ResponseEntity<?> getDefaultContact() {
        Assert.notNull(User.getContext(), message.getMessage("authentication.required"));
        User user = User.getContext();
        Map<String, Object> response = new HashMap<>();
        response.put("address", user.getAddress());
        response.put("phone", user.getPhone());
        response.put("fullName", user.getFullName());
        return ResponseEntity.ok(response);
    }

    @Override
    public Page<Order> findBySearch(CommonRequest model) {
        return this.orderRepo.findBySearch(model, model.getPageable());
    }

    @Override
    @Transactional(rollbackFor = IllegalArgumentException.class)
    public ResponseEntity<?> approve(OrderActionRequest model) throws MessagingException, IOException {
        Order order = orderRepo.getByIdAndStatusIn(model.getId(), List.of(OrderConst.STATUS_PENDING, OrderConst.STATUS_PROCESSING));
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        order.getOrderDetails().forEach(orderDetail -> {
            ProductDetail productDetail = orderDetail.getProductDetail();
            productDetail.setQuantity(productDetail.getQuantity() - orderDetail.getQuantity());
        });
        order.setStatus(OrderConst.STATUS_PROCESSING);
        if (order.getUser().getEmail() != null) {
            Order order1 = orderRepo.save(order);
            Executor executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                try {
                    emailSender.sendOrderAlert(order1);
                } catch (IOException | MessagingException e) {
                    e.printStackTrace();
                }
            });
        } else {
            orderRepo.save(order);
        }
        return ResponseEntity.ok(new AlertResponseRecord(message.getMessage("order.approve-success"), HttpStatus.OK.value()));
    }

    @Override
    @Transactional(rollbackFor = IllegalArgumentException.class)
    public ResponseEntity<?> reject(OrderActionRequest model) {
        Order order = orderRepo.getByIdAndStatusIn(model.getId(), List.of(OrderConst.STATUS_PENDING, OrderConst.STATUS_PROCESSING));
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        order.setStatus(OrderConst.STATUS_REJECTED);
        order.setNote(model.getReason());
        orderRepo.save(order);
        this.rollbackVoucher(order);
        return ResponseEntity.ok(new AlertResponseRecord(message.getMessage("order.reject-success"), HttpStatus.OK.value()));
    }

    @Override
    @Transactional(rollbackFor = IllegalArgumentException.class)
    public ResponseEntity<?> shipping(OrderActionRequest model) {
        Order order = orderRepo.getByIdAndStatusIn(model.getId(), List.of(OrderConst.STATUS_PROCESSING));
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        order.setStatus(OrderConst.STATUS_SHIPPING);
        orderRepo.save(order);
        return ResponseEntity.ok(new AlertResponseRecord(message.getMessage("order.do-shipping"), HttpStatus.OK.value()));
    }

    @Override
    @Transactional(rollbackFor = IllegalArgumentException.class)
    public ResponseEntity<?> receivedOrder(OrderActionRequest model) {
        Order order = orderRepo.getByIdAndStatusIn(model.getId(), List.of(OrderConst.STATUS_SHIPPING));
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        order.setStatus(OrderConst.STATUS_COMPLETED);
        orderRepo.save(order);
        return ResponseEntity.ok(new AlertResponseRecord(message.getMessage("order.received"), HttpStatus.OK.value()));
    }

    @Override
    public Page<Order> findByUser(CommonRequest model) {
        Sort sort = Sort.by(Sort.Order.desc("createDate"));
        Pageable pageable = model.getPageable(sort);
        return this.orderRepo.findByUserId(User.getContextId(), pageable);
    }

    private void rollbackVoucher(Order order) {
        if (order == null) return;
        if (order.getVoucher() != null) {
            Voucher voucher = order.getVoucher();
            voucher.setUsage(voucher.getUsage() - 1);
            this.voucherRepo.save(voucher);
        }
    }

}
