package io.github.pudo58.endpoint;

import io.github.pudo58.base.entity.Order;
import io.github.pudo58.base.service.OrderService;
import io.github.pudo58.dto.CommonRequest;
import io.github.pudo58.dto.OrderActionRequest;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/findBySearch")
    public Page<Order> findBySearch(@RequestBody CommonRequest model) {
        return orderService.findBySearch(model);
    }

    @PostMapping("/approve")
    public ResponseEntity<?> approve(@RequestBody OrderActionRequest model) throws MessagingException, IOException {
        return orderService.approve(model);
    }

    @PostMapping("/reject")
    public ResponseEntity<?> reject(@RequestBody OrderActionRequest model) {
        return orderService.reject(model);
    }

    @PostMapping("/shipping")
    public ResponseEntity<?> shipping(@RequestBody OrderActionRequest model) {
        return orderService.shipping(model);
    }

    @GetMapping("/get/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable UUID orderId) {
        return orderService.getOrder(orderId);
    }
}
