package com.kim.washing_machine.service;

import com.kim.washing_machine.dto.request.CreateWashingRequest;
import com.kim.washing_machine.dto.response.CreateWashingResponse;
import com.kim.washing_machine.dto.response.ReadWashingResponse;
import com.kim.washing_machine.entity.Washing;
import com.kim.washing_machine.repository.WashingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(rollbackOn = Exception.class) // Exception의 하위 클레스에서 예외인 모든 상황에서 모두 취소 한다
@RequiredArgsConstructor // final이 붙어 있는 함수의 생성자 자동 제작
public class WashingService {
    private final WashingRepository washingRepository;

    public CreateWashingResponse createWashing(CreateWashingRequest request) {
        Washing washing = Washing.builder()
                .room(request.room())
                .position(request.position())
                .build();
        String[] pos = {"3층 실습동편", "3층 실습동 반대편", "4층 실습동편", "4층 실습동 반대편", "5층 실습동편", "5층 실습동 반대편"};
        washingRepository.save(washing);
        return new CreateWashingResponse(washing.getRoom() + "호, " + pos[washing.getPosition() - 1] +" 위치 예약되었습니다.");
    }

    public List<ReadWashingResponse> readWashing() {
        List<Washing> washings = washingRepository.findAll();

        if (washings.isEmpty()) {
            throw new NoSuchElementException("현재 대기 중인 세탁이 없습니다.");
        }

        return ReadWashingResponse.fromList(washings);
    }

    public List<ReadWashingResponse> searchPositionWashing(int position) {
        List<Washing> washings = washingRepository.findByPosition(position);

        if (washings.isEmpty()) {
            throw new NoSuchElementException("예약이 없습니다.");
        }

        return ReadWashingResponse.fromList(washings);
    }

    public List<ReadWashingResponse> searchRoomWashing(String room) {
        List<Washing> washing = washingRepository.findByRoom(room);

        if (washing.isEmpty()) {
            throw new NoSuchElementException("에약을 찾을 수 없습니다.");
        }

        return ReadWashingResponse.fromList(washing);
    }

    public String finishWashing(Long id) {
        Washing washing = washingRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("예약을 찾을 수 없습니다."));
        washing.finishWashing();
        return washing.getRoom() + "호 세탁이 완료되었습니다.";
    }

    public String deleteWashing(Long id) {
        Washing washing = washingRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("예약이 안되어 있습니다.")); // 값이 없을 떄
        washingRepository.deleteById(id);

        return washing.getRoom() + "호 예약이 취소되었습니다.";
    }

    public String resetWashing() {
        if (washingRepository.count() == 0) {
            throw new NoSuchElementException("삭제할 예약이 없습니다.");
        }

        washingRepository.deleteAll();
        washingRepository.resetAutoIncrement();
        return "예약을 초기화 했습니다.";
    }

    public String deleteDoneWashing(boolean done) {
        List<Washing> washings = washingRepository.findByDone(done);

        if (washings.isEmpty()) {
            throw new NoSuchElementException("완료된 예약이 없습니다.");
        }

        washingRepository.deleteAll(washings);

        return "완료된 예약을 삭제했습니다.";
    }
}
