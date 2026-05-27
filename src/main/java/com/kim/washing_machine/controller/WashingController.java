package com.kim.washing_machine.controller;

import com.kim.washing_machine.common.ApiResponse;
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
@RequestMapping("/washing")
@RequiredArgsConstructor
public class WashingController {
    private final WashingService washingService;

    @PostMapping
    public ResponseEntity<CreateWashingResponse> createWashing(
            @RequestBody @Valid CreateWashingRequest request // RequestBody는 요청에서 json 데이터를 꺼내서 CreateWashingRequest 객체로 자동 변환
    ) {
        CreateWashingResponse response = washingService.createWashing(request); // 데이터 받음
        return ResponseEntity.status(HttpStatus.CREATED).body(response); // 응답 코드 보냄
    }

    @GetMapping
    public ResponseEntity<List<ReadWashingResponse>> readWashing() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(washingService.readWashing());
    }

    @GetMapping("/yet")
    public ResponseEntity<List<ReadWashingResponse>> readYetWashing() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(washingService.readYetWashing());
    }

    @GetMapping("/position/{position}")
    public ResponseEntity<List<ReadWashingResponse>> findPositionWashing(@PathVariable int position) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(washingService.searchPositionWashing(position));
    }

    @GetMapping("/room/{room}")
    public ResponseEntity<List<ReadWashingResponse>> findRoomWashing(@PathVariable String room) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(washingService.searchRoomWashing(room));
    }

    @PatchMapping("/finish/{id}")
    public ResponseEntity<ApiResponse<ReadWashingResponse>> finishWashing(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(washingService.finishWashing(id)));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteWashing(@PathVariable Long id) {
        washingService.deleteWashing(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/done")
    public ResponseEntity<String> deleteDoneWashing() {
        washingService.deleteDoneWashing();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/reset")
    public ResponseEntity<Void> resetWashing() {
        washingService.resetWashing();
        return ResponseEntity.noContent().build();
    }

    // @PathVariable: url에서 값을 받아옴
}