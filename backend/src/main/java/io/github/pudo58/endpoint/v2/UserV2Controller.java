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

    @GetMapping("/profile")
    public UserRecord profile() {
        return this.userService.get();
    }

}
