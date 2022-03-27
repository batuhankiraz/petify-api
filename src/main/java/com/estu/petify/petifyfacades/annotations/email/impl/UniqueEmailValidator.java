package com.estu.petify.petifyfacades.annotations.email.impl;

import com.estu.petify.petifycore.model.UserModel;
import com.estu.petify.petifycore.service.UserService;
import com.estu.petify.petifyfacades.annotations.email.UniqueEmail;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final UserService defaultUserService;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext ctx) {
        List<UserModel> userList = defaultUserService.getUserByEmail(email);
        return CollectionUtils.isEmpty(userList);
    }
}
