package com.kim.washing_machine.controller;

import com.kim.washing_machine.dto.request.CreateWashingRequest;
import com.kim.washing_machine.dto.response.CreateWashingResponse;
import com.kim.washing_machine.dto.response.ReadWashingResponse;
import com.kim.washing_machine.service.WashingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WashingController {
    private final WashingService washingService;

    @PostMapping("/washing")
    public ResponseEntity<CreateWashingResponse> createWashing(
            @RequestBody @Valid CreateWashingRequest request // RequestBody는 요청에서 json 데이터를 꺼내서 CreateWashingRequest 객체로 자동 변환
    ) {
        CreateWashingResponse response = washingService.createWashing(request); // 데이터 받음
        return ResponseEntity.status(HttpStatus.CREATED).body(response); // 응답 코드 보냄
    }

    @GetMapping("/washing")
    public ResponseEntity<List<ReadWashingResponse>> readWashing() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(washingService.readWashing());
    }

    @GetMapping("/washing/yet")
    public ResponseEntity<List<ReadWashingResponse>> readYetWashing() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(washingService.readYetWashing());
    }

    @GetMapping("/washing/position/{position}")
    public ResponseEntity<List<ReadWashingResponse>> findPositionWashing(@PathVariable int position) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(washingService.searchPositionWashing(position));
    }

    @GetMapping("/washing/room/{room}")
    public ResponseEntity<List<ReadWashingResponse>> findRoomWashing(@PathVariable String room) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(washingService.searchRoomWashing(room));
    }

    @PatchMapping("/washing/finish/{id}")
    public ResponseEntity<String> finishWashing(@PathVariable Long id) {
        String response = washingService.finishWashing(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/washing/delete/{id}")
    public ResponseEntity<String> deleteWashing(@PathVariable Long id) {
        String response = washingService.deleteWashing(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/washing/delete/done")
    public ResponseEntity<String> deleteDoneWashing() {
        String response = washingService.deleteDoneWashing(true);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("/washing/reset")
    public ResponseEntity<String> resetWashing() {
        String response = washingService.resetWashing();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // @PathVariable: url에서 값을 받아옴
}
