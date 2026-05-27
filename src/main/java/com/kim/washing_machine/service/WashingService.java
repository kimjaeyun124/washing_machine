package com.kim.washing_machine.service;

import com.kim.washing_machine.dto.request.CreateWashingRequest;
import com.kim.washing_machine.dto.response.CreateWashingResponse;
import com.kim.washing_machine.dto.response.ReadWashingResponse;
import com.kim.washing_machine.entity.Washing;
import com.kim.washing_machine.exception.ErrorCode;
import com.kim.washing_machine.exception.WashingException;
import com.kim.washing_machine.repository.WashingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackOn = Exception.class) // Exception의 하위 클레스에서 예외인 모든 상황에서 모두 취소 한다
@RequiredArgsConstructor // final이 붙어 있는 함수의 생성자 자동 제작
public class WashingService {
    private final WashingRepository washingRepository;

    public CreateWashingResponse createWashing(CreateWashingRequest request) {
        return CreateWashingResponse.of(
                washingRepository.save(
                        Washing.builder()
                                .room(request.room())
                                .position(request.position())
                                .build()
                )
        );
    }

    public List<ReadWashingResponse> readWashing() {
        return ReadWashingResponse.fromList(
                Optional.of(washingRepository.findAll())
                        .filter(list -> !list.isEmpty())
                        .orElseThrow(() -> new WashingException(ErrorCode.WASHING_LIST_EMPTY))
        );
    }

    public List<ReadWashingResponse> readYetWashing() {
        return ReadWashingResponse.fromList(
                Optional.of(washingRepository.findByDone(false))
                        .filter(list -> !list.isEmpty())
                        .orElseThrow(() -> new WashingException(ErrorCode.WASHING_LIST_EMPTY))
        );
    }

    public List<ReadWashingResponse> searchPositionWashing(int position) {
        return ReadWashingResponse.fromList(
                Optional.of(washingRepository.findByPosition(position))
                        .filter(list -> !list.isEmpty())
                        .orElseThrow(() -> new WashingException(ErrorCode.WASHING_NOT_FOUND))
        );
    }

    public List<ReadWashingResponse> searchRoomWashing(String room) {
        return ReadWashingResponse.fromList(
                Optional.of(washingRepository.findByRoom(room))
                        .filter(list -> !list.isEmpty())
                        .orElseThrow(() -> new WashingException(ErrorCode.WASHING_NOT_FOUND))
        );
    }

    public ReadWashingResponse finishWashing(Long id) {
        Washing washing = washingRepository.findById(id)
                .orElseThrow(() -> new WashingException(ErrorCode.WASHING_NOT_FOUND));
        washing.finishWashing();
        return ReadWashingResponse.of(washing);
    }

    public void deleteWashing(Long id) {
        washingRepository.deleteById(
                washingRepository.findById(id)
                        .orElseThrow(() -> new WashingException(ErrorCode.WASHING_NOT_FOUND))
                        .getId()
        );
    }

    public void deleteDoneWashing() {
        washingRepository.deleteAll(
                Optional.of(washingRepository.findByDone(true))
                        .filter(list -> !list.isEmpty())
                        .orElseThrow(() -> new WashingException(ErrorCode.WASHING_DONE_EMPTY))
        );
    }

    public void resetWashing() {
        Optional.of(washingRepository.findAll())
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new WashingException(ErrorCode.WASHING_RESET_EMPTY));
    }
}
