package com.spring6.user.validations;

import com.spring6.common.utils.FileUploadUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.IOException;

public class ImageExtensionValidator implements ConstraintValidator<ValidImageExtension, MultipartFile> {
    @Override
    public void initialize(ValidImageExtension constraintAnnotation) {
    }

    @Override
    public boolean isValid(MultipartFile imageFile, ConstraintValidatorContext context) {
        if (imageFile == null || imageFile.isEmpty()) {
            return true; // Skip validation if no image is provided
        }

        try {
            // Check the image extension using ImageIO

            String extension = FileUploadUtils.getExtensionByString(imageFile.getOriginalFilename()).get();
            if(extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("jpeg")) {
                ImageIO.read(imageFile.getInputStream());
                return true; // Valid image extension
            }
            return false;

        } catch (IOException e) {
            return false; // Invalid image extension
        }
    }
}
