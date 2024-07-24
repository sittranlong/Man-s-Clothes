package io.github.pudo58.base.config;

import io.github.pudo58.base.service.ScheduleJobService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class ScheduleJobConfig {
    private final ScheduleJobService scheduleJobService;

    @Scheduled(cron = "0 0 8 * * *")
    public void sendEmailReminder() {
        scheduleJobService.sendEmailReminder();
    }

    // mỗi 1 phút
    @Scheduled(cron = "0 * * * * *") // mỗi 1 phút
    public void changeStatusOrderDelivered() {
        scheduleJobService.changeStatusOrderDelivered();
    }
}
