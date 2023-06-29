package com.spring6.user.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserStatusValidator.class)
public @interface ValidUserStatus {
    String message() default "Invalid user status";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
