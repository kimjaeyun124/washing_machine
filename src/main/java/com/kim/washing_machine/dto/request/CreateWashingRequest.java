package com.kim.washing_machine.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CreateWashingRequest(
        @NotBlank(message = "방번호 입력은 필수입니다.")
        String room,
        @Min(value = 1, message = "사용가능한 위치는 1부터 시작")
        @Max(value = 6, message = "사용가능한 위치는 6까지 있음")
        int position
) {}
