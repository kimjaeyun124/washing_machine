package com.kim.washing_machine.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    WASHING_NOT_FOUND(HttpStatus.NOT_FOUND, "예약을 찾을 수 없습니다."),
    WASHING_LIST_EMPTY(HttpStatus.NOT_FOUND, "현재 대기 중인 세탁이 없습니다."),
    WASHING_DONE_EMPTY(HttpStatus.NOT_FOUND, "완료된 예약이 없습니다."),
    WASHING_RESET_EMPTY(HttpStatus.NOT_FOUND, "삭제할 예약이 없습니다.");

    private final HttpStatus status;
    private final String message;
}
