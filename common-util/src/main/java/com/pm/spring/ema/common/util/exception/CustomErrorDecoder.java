package com.pm.spring.ema.common.util.exception;

import com.pm.spring.ema.common.util.HttpStatusCodes;
import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 404) {
            return new FeignClientException(HttpStatusCodes.NOT_FOUND, "Resource not Found");
        } else if (response.status() == 500) {
            return new FeignClientException(HttpStatusCodes.INTERNAL_SERVER_ERROR, "Internal Server Error");
        } else {
            return new FeignClientException(HttpStatusCodes.BAD_REQUEST, "Bad Request");
        }
    }
}