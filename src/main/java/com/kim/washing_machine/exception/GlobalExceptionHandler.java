package com.kim.washing_machine.exception;

import com.kim.washing_machine.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice // Controller에서 생성되는 예외를 모두 전역으로 잡아서 처리
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class) // @Valid로 생기는 오류(MethodArguemntNotValidException.class)를 잡아서 예외 처리
    public ResponseEntity<ApiResponse<Void>> handleValidException(
            MethodArgumentNotValidException e
    ) {
        String message = e.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.fail(message)); // 400 Bad Request 상태코드, 오류 Map 반환
    }

    @ExceptionHandler(WashingException.class)
    public ResponseEntity<ApiResponse<Void>> handleWashingException(
            WashingException e
    ) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getStatus()).body(ApiResponse.fail(errorCode.getMessage()));
    }
}
