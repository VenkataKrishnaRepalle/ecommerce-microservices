package com.spring6.user.validations;

import com.spring6.user.enums.UserStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;

public class UserStatusValidator implements ConstraintValidator<ValidUserStatus, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(value)) {
            return true; // Allow empty string if desired
        }
        return EnumUtils.isValidEnum(UserStatus.class, value);
    }
}
