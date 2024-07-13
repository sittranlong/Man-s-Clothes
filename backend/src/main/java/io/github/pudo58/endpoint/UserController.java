package io.github.pudo58.endpoint;

import io.github.pudo58.base.entity.User;
import io.github.pudo58.base.service.UserService;
import io.github.pudo58.dto.CommonRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/findBySearch")
    public Page<User> findBySearch(@RequestBody CommonRequest model) {
        return userService.findBySearch(model);
    }

    @GetMapping("/get/{id}")
    public User get(@PathVariable UUID id) {
        return userService.getById(id);
    }
}
