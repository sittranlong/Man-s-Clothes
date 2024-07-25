package io.github.pudo58.base.service;

import io.github.pudo58.base.entity.Order;
import io.github.pudo58.base.entity.User;
import io.github.pudo58.base.repo.OrderRepo;
import io.github.pudo58.base.repo.UserRepo;
import io.github.pudo58.constant.OrderConst;
import io.github.pudo58.constant.UserConst;
import io.github.pudo58.util.EmailSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleJobServiceImpl implements ScheduleJobService {
    private final UserRepo userRepo;
    private final EmailSender emailSender;
    private final OrderRepo orderRepo;

    @Override
    public void sendEmailReminder() {
        User exampleUser = new User();
        exampleUser.setStatus(UserConst.STATUS_NON_EMAIL_VERIFIED);
        exampleUser.setCreateDate(null);
        exampleUser.setModifiedDate(null);
        List<User> userList = userRepo.findAll(Example.of(exampleUser));
        userList.forEach(user -> {
            try {
                emailSender.sendReminder(user);
                log.info("Sent reminder to user: {}", user.getUsername());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    @Transactional
    public void changeStatusOrderReceived() {
        List<Order> orderList = orderRepo.findByStatusCustom(OrderConst.STATUS_SHIPPING);
        for (Order order : orderList) {
            order.setStatus(OrderConst.STATUS_RECEIVED);
            orderRepo.save(order);
        }
    }

    @Override
    @Transactional
    public void changeStatusOrderDelivered() {
        List<Order> orderList = orderRepo.findByStatus(OrderConst.STATUS_RECEIVED);
        for (Order order : orderList) {
            // nếu ngày nhận hàng + 3 ngày < ngày hiện tại
            if (order.getDeliveryDate().toInstant().plusSeconds(3 * 24 * 60 * 60).toEpochMilli() < System.currentTimeMillis()) {
                order.setStatus(OrderConst.STATUS_COMPLETED);
                orderRepo.save(order);
            }
        }
    }
}
