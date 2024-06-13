package io.github.pudo58.base.service;

import io.github.pudo58.base.entity.*;
import io.github.pudo58.base.repo.CartRepo;
import io.github.pudo58.base.repo.OrderRepo;
import io.github.pudo58.base.repo.VoucherRepo;
import io.github.pudo58.constant.OrderConst;
import io.github.pudo58.constant.VoucherConst;
import io.github.pudo58.dto.OrderRequest;
import io.github.pudo58.record.AlertResponseRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;
    private final CartRepo cartRepo;
    private final VoucherRepo voucherRepo;

    @Value("${vietqr.url}")
    private String vietQRURL;
    @Value("${shipping.fee}")
    private int shippingFee;


    @Override
    @Transactional(rollbackFor = IllegalArgumentException.class)
    public ResponseEntity<?> createOrder(OrderRequest model) throws NoSuchAlgorithmException, IOException, InterruptedException, URISyntaxException {
        User user = User.getContext();
        Assert.notNull(user, "Bạn cần đăng nhập để thực hiện chức năng này");
        List<Cart> cartList = this.cartRepo.findByUserId(user.getId());
        if (cartList.isEmpty()) {
            return ResponseEntity.badRequest().body(new AlertResponseRecord("Giỏ hàng của bạn đang trống", HttpStatus.BAD_REQUEST.value()));
        } else {
            List<OrderDetail> orderDetailList = OrderDetail.fromCartList(cartList);
            Order order = new Order();
            order.setUser(user);
            order.setOrderDetails(orderDetailList);
            order.setAddress(model.getAddress());
            order.setPhone(model.getPhone());
            order.setNote(model.getNote());
            order.setPaymentMethod(model.getPaymentMethod());
            order.setShippingFee(shippingFee);
            order.setTotal(cartList.stream().mapToInt(cart -> cart.getProductDetail().getProduct().getPrice() * cart.getQuantity()).sum());
            order.setFinalTotal(order.getTotal());
            Voucher voucher = voucherRepo.findByCode(model.getVoucherCode()).orElse(null);
            if (voucher != null) {
                if (VoucherConst.STATUS_INACTIVE.equals(voucher.getStatus())) {
                    return ResponseEntity.badRequest().body(new AlertResponseRecord("Voucher không hợp lệ", HttpStatus.BAD_REQUEST.value()));
                }
                // check startDate, endDate
                Date currentDate = new Date();
                if (currentDate.before(voucher.getStartDate()) || currentDate.after(voucher.getEndDate())) {
                    return ResponseEntity.badRequest().body(new AlertResponseRecord("Voucher không hợp lệ", HttpStatus.BAD_REQUEST.value()));
                }
                if (voucher.getMaxUsage() != null && voucher.getUsage() >= voucher.getMaxUsage()) {
                    return ResponseEntity.badRequest().body(new AlertResponseRecord("Voucher đã hết lượt sử dụng", HttpStatus.BAD_REQUEST.value()));
                }
                if (voucher.getMinTotal() != null && order.getFinalTotal() < voucher.getMinTotal()) {
                    return ResponseEntity.badRequest().body(new AlertResponseRecord("Voucher không hợp lệ", HttpStatus.BAD_REQUEST.value()));
                }
                order.setVoucher(voucher);
                voucher.setUsage(voucher.getUsage() + 1);
                voucherRepo.save(voucher);

                // calculate discount
                int discount = 0;
                if (VoucherConst.TYPE_PERCENT.equals(voucher.getType())) {
                    discount = (order.getFinalTotal() * voucher.getDiscount() / 100);
                    if (discount > voucher.getMaxDiscount()) {
                        discount = voucher.getMaxDiscount();
                    }
                } else if (VoucherConst.TYPE_FIXED.equals(voucher.getType())) {
                    discount = voucher.getDiscount();
                }
                order.setDiscount(discount);
                order.setFinalTotal(order.getFinalTotal() - discount + shippingFee);
            } else {
                order.setFinalTotal(order.getFinalTotal() + shippingFee);
                order.setDiscount(0);
            }
            Order orderSaved = this.orderRepo.save(order);
            this.cartRepo.deleteAll(cartList);
            ResponseEntity<?> qrCode = this.getQrCode(orderSaved.getId());
            Map<String, Object> response = Map.of(
                    "order", orderSaved,
                    "qrCode", Objects.requireNonNull(qrCode.getBody())
            );
            return ResponseEntity.ok(response);
        }
    }

    @Override
    public ResponseEntity<?> getQrCode(UUID orderId) throws NoSuchAlgorithmException, IOException, InterruptedException, URISyntaxException {
        Assert.notNull(User.getContext(), "Bạn cần đăng nhập để thanh toán");
        Order order = orderRepo.getByIdAndStatusIn(orderId, List.of(OrderConst.STATUS_PENDING, OrderConst.STATUS_PROCESSING));
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        final String jsonPayload = "{\n" +
                "  \"accountNo\": \"0704145768\",\n" +
                "  \"accountName\": \"LA VAN THO\",\n" +
                "  \"acqId\": 970432,\n" +
                "  \"amount\": " + order.getFinalTotal() + ",\n" +
                "  \"addInfo\": \"" + User.getContext().getUsername() + "- Thanh toan tour gia tri " + order.getFinalTotal() + " VND\",\n" +
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
        return ResponseEntity.ok(new AlertResponseRecord("Hủy đơn hàng thành công", HttpStatus.OK.value()));
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
        orderRepo.save(order);
        return ResponseEntity.ok(new AlertResponseRecord("Xác nhận đơn hàng thành công", HttpStatus.OK.value()));
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
