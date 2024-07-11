package io.github.pudo58.aop;

import io.github.pudo58.base.entity.User;
import io.github.pudo58.base.repo.CartRepo;
import io.github.pudo58.base.repo.UserRepo;
import io.github.pudo58.constant.UserConst;
import io.github.pudo58.dto.OrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class OrderAspect {
    private final UserRepo userRepo;
    private final CartRepo cartRepo;

    @Before("execution(* io.github.pudo58.endpoint.v2.OrderV2Controller.*(..)) args(..)")
    public void orderBefore() {
        UUID userId = User.getContextId();
        User user = userRepo.findById(userId).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        if (UserConst.STATUS_NON_EMAIL_VERIFIED.equals(user.getStatus())) {
            throw new IllegalArgumentException("Bạn phải xác thực email để sử dụng chức năng này");
        }
    }

    @After("execution(* io.github.pudo58.endpoint.v2.OrderV2Controller.createOrder(..))")
    public void orderAfter(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof OrderRequest orderRequest) {
                for (UUID cartId : orderRequest.getCartIdList()) {
                    cartRepo.deleteById(cartId);
                }
                log.info("Delete cart success");
            }
        }
    }

}
