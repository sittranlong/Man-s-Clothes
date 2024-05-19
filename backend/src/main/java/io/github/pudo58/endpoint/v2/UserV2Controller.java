package io.github.pudo58.endpoint.v2;

import io.github.pudo58.base.service.UserService;
import io.github.pudo58.record.AlertResponseRecord;
import io.github.pudo58.record.UserRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2/user")
@RequiredArgsConstructor
public class UserV2Controller {
    private final UserService userService;

    @GetMapping("/get")
    public UserRecord get() {
        return this.userService.get();
    }

    @PostMapping("/sendOtp")
    public AlertResponseRecord sendOtp() {
        return this.userService.sendOtp();
    }

    @PostMapping("/verifyOtp")
    public AlertResponseRecord verifyOtp(@RequestParam String otp) {
        return this.userService.verifyOtp(otp);
    }
}
