package io.github.pudo58.base.service;

import io.github.pudo58.base.entity.Order;
import io.github.pudo58.dto.CommonRequest;
import io.github.pudo58.dto.OrderActionRequest;
import io.github.pudo58.dto.OrderRequest;
import io.github.pudo58.record.AlertResponseRecord;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public interface OrderService {
    void saveContact(OrderRequest model);
    ResponseEntity<?> createOrder(OrderRequest model) throws NoSuchAlgorithmException, IOException, InterruptedException, URISyntaxException;

    ResponseEntity<?> getOrderInfo(OrderRequest model) throws URISyntaxException, NoSuchAlgorithmException, IOException, InterruptedException;

    ResponseEntity<?> getQrCode(UUID orderId) throws NoSuchAlgorithmException, IOException, InterruptedException, URISyntaxException;

    ResponseEntity<?> cancelOrder(OrderRequest model);

    ResponseEntity<?> getOrder(UUID orderId);

    ResponseEntity<?> approveOrder(UUID orderId);

    ResponseEntity<?> getDefaultContact();

//    for admin
    Page<Order> findBySearch(CommonRequest model);

    ResponseEntity<?> approve(OrderActionRequest model);

    ResponseEntity<?> reject(OrderActionRequest model);

    ResponseEntity<?> shipping(OrderActionRequest model);
}
