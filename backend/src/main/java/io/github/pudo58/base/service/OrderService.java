package io.github.pudo58.base.service;

import io.github.pudo58.dto.OrderRequest;
import io.github.pudo58.record.AlertResponseRecord;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public interface OrderService {
    ResponseEntity<?> createOrder(OrderRequest model) throws NoSuchAlgorithmException, IOException, InterruptedException, URISyntaxException;

    ResponseEntity<?> getQrCode(UUID orderId) throws NoSuchAlgorithmException, IOException, InterruptedException, URISyntaxException;

    ResponseEntity<?> cancelOrder(OrderRequest model);

    ResponseEntity<?> getOrder(UUID orderId);

    ResponseEntity<?> approveOrder(UUID orderId);
}
