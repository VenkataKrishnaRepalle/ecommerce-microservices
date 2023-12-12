//package com.pm.spring.ema.gateway.service.exception;
//
//import brave.Tracer;
//import com.pm.spring.ema.common.util.GlobalConstants;
//import com.pm.spring.ema.common.util.exception.ErrorCodes;
//import com.pm.spring.ema.common.util.exception.ErrorMessage;
//import com.pm.spring.ema.common.util.exception.ErrorResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@RequiredArgsConstructor
//@RestControllerAdvice
//public class GlobalMessageExceptionHandler {
//    private final Tracer tracer;
//
//    @ExceptionHandler(AuthHeaderNotFoundException.class)
//    public ResponseEntity<ErrorResponse> handleAuthHeaderNotFoundException(AuthHeaderNotFoundException exception) {
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(GlobalConstants.TRACE_ID_HEADER, tracer.currentSpan().context().traceIdString());
//
//        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                .headers(headers)
//                .body(ErrorMessage.errorResponse(exception.getErrorCode()));
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(GlobalConstants.TRACE_ID_HEADER, tracer.currentSpan().context().traceIdString());
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .headers(headers)
//                .body(ErrorMessage.errorResponse(ErrorCodes.E0500, exception.getMessage()));
//    }
//}
