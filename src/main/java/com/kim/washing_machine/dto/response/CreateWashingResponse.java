package com.kim.washing_machine.dto.response;

import com.kim.washing_machine.entity.Washing;

public record CreateWashingResponse(
        Long id,
        String room,
        int position,
        String positionName,
        boolean done
) {
    private static final String[] POSITION_NAMES = {
            "3층 실습동편",
            "3층 실습동 반대편",
            "4층 실습동편",
            "4층 실습동 반대편",
            "5층 실습동편",
            "5층 실습동 반대편"
    };

    public static CreateWashingResponse of(Washing washing) {
        return new CreateWashingResponse(
                washing.getId(),
                washing.getRoom(),
                washing.getPosition(),
                POSITION_NAMES[washing.getPosition() - 1],

                washing.isDone()
        );
    }
}
