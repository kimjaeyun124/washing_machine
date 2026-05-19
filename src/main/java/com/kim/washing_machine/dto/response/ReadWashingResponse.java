package com.kim.washing_machine.dto.response;

import com.kim.washing_machine.entity.Washing;

import java.util.List;

public record ReadWashingResponse(
        Long id,
        String room,
        int position,
        boolean done) {
    public static ReadWashingResponse of(Washing washing) {
        return new ReadWashingResponse(
                washing.getId(),
                washing.getRoom(),
                washing.getPosition(),
                washing.isDone()
        );
    }

    public  static List<ReadWashingResponse> fromList(List<Washing> washings) {
        return washings.stream()
                .map(ReadWashingResponse::of)
                .toList();
    }
}
