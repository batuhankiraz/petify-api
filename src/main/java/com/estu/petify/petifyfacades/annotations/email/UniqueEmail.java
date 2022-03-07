package com.estu.petify.petifyfacades.annotations.email;


import com.estu.petify.petifyfacades.annotations.email.impl.UniqueEmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

@Target( { FIELD, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = UniqueEmailValidator.class)
public @interface UniqueEmail {

    public String message() default "There is already an account associated with the email you entered.";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}
