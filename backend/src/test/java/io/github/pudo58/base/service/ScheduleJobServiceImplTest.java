package io.github.pudo58.base.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ScheduleJobServiceImplTest {
    private final ScheduleJobServiceImpl scheduleJobService;

    @Autowired
    public ScheduleJobServiceImplTest(ScheduleJobServiceImpl scheduleJobService) {
        this.scheduleJobService = scheduleJobService;
    }

    @Test
    void changeStatusOrderReceived() {
        scheduleJobService.changeStatusOrderReceived();
    }
}