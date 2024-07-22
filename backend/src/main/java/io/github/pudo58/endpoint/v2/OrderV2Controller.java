package io.github.pudo58.endpoint.v2;

import io.github.pudo58.base.entity.Order;
import io.github.pudo58.base.service.OrderService;
import io.github.pudo58.dto.CommonRequest;
import io.github.pudo58.dto.OrderActionRequest;
import io.github.pudo58.dto.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@RestController
@RequestMapping("/v2/order")
@RequiredArgsConstructor
public class OrderV2Controller {
    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest model) throws NoSuchAlgorithmException, IOException, InterruptedException, URISyntaxException {
        return this.orderService.createOrder(model);
    }

    @PostMapping("/getOrderInfo")
    public ResponseEntity<?> getOrderInfo(@RequestBody OrderRequest model) throws URISyntaxException, NoSuchAlgorithmException, IOException, InterruptedException {
        return this.orderService.getOrderInfo(model);
    }

    @GetMapping("/getQrCode/{orderId}")
    public ResponseEntity<?> getQrCode(@PathVariable UUID orderId) throws NoSuchAlgorithmException, IOException, InterruptedException, URISyntaxException {
        return this.orderService.getQrCode(orderId);
    }

    @PostMapping("/cancel")
    public ResponseEntity<?> cancelOrder(@RequestBody OrderRequest model) {
        return this.orderService.cancelOrder(model);
    }

    @GetMapping("/get/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable UUID orderId) {
        return this.orderService.getOrder(orderId);
    }

    @PostMapping("/saveContact")
    public void saveContact(@RequestBody OrderRequest model) {
        this.orderService.saveContact(model);
    }

    @GetMapping("/getDefaultContact")
    public ResponseEntity<?> getDefaultContact() {
        return this.orderService.getDefaultContact();
    }

    // for user
    @PostMapping("/findByUser")
    public Page<Order> findByUser(@RequestBody CommonRequest model) {
        return this.orderService.findByUser(model);
    }

    @PostMapping("/received")
    public ResponseEntity<?> receivedOrder(@RequestBody OrderActionRequest model) {
        return this.orderService.receivedOrder(model);
    }
}
