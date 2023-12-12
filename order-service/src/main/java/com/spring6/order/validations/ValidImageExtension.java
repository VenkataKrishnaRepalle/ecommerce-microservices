package com.pm.spring.ema.order.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ImageExtensionValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidImageExtension {
    String message() default "Invalid image extension, only supported .png and .jpg";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
