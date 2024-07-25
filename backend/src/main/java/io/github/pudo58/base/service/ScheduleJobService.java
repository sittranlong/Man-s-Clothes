package io.github.pudo58.base.service;

public interface ScheduleJobService {
    void sendEmailReminder();

    void changeStatusOrderReceived();

    void changeStatusOrderDelivered(); // tự động cập nhật trạng thái đơn hàng đã giao
}
