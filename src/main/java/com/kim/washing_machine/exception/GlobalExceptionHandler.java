package com.kim.washing_machine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice // Controller에서 생성되는 예외를 모두 전역으로 잡아서 처리
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class) // @Valid로 생기는 오류(MethodArguemntNotValidException.class)를 잡아서 예외 처리
    public ResponseEntity<Map<String, String>> handleValidException(
            MethodArgumentNotValidException e
    ) {
        Map<String, String> errors = new HashMap<>(); // 오류를 담을 빈 Map생성, <String("room"), String("방번호 입력 필수)>
        e.getBindingResult().getFieldErrors()
        // 400 Bad Request 상태코드와 함께 오류 Map을 반환
        // GetBindingResult(): 검증 결과 전체
        // getFieldErrors(): 검증 실패한 필드 목록
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
                // 각 오류를 Map에 넣음
                // error.getField(): 오류 난 필드명 "room"
                // error.getDefaultMessage(): 오류 메시지
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors); // 400 Bad Request 상태코드, 오류 Map 반환
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, String>> handleNotFoundException(
            NoSuchElementException e
    ) {
        Map<String, String> errors = new HashMap<>();
        errors.put("message", e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }
}
