package io.github.pudo58.endpoint.v2;

import io.github.pudo58.base.entity.User;
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
    public User profile() {
        return this.userService.getById(User.getContextId());
    }

    @PostMapping("/update")
    public User update(@RequestBody User model) {
        return this.userService.update(User.getContextId(), model);
    }

}
