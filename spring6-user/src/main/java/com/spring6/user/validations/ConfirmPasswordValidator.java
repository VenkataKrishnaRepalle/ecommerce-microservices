package com.spring6.user.validations;

import com.spring6.user.dto.UserCreateRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ConfirmPasswordValidator implements ConstraintValidator<ConfirmPassword, Object> {

    @Override
    public void initialize(ConfirmPassword confirmPassword) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        if (obj == null) {
            return true;
        }

        UserCreateRequestDto formDto = (UserCreateRequestDto) obj;
        return formDto.getPassword().equals(formDto.getConfirmPassword());
    }
}
