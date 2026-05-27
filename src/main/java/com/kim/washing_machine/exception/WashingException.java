package com.kim.washing_machine.exception;

import lombok.Getter;

@Getter
public class WashingException extends RuntimeException {

    private final ErrorCode errorCode;

    public WashingException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
