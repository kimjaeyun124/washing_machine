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
    public ResponseEntity<ApiResponse<CreateWashingResponse>> createWashing(
            @RequestBody @Valid CreateWashingRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(washingService.createWashing(request)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ReadWashingResponse>>> readWashing() {
        return ResponseEntity.ok(ApiResponse.ok(washingService.readWashing()));
    }

    @GetMapping("/yet")
    public ResponseEntity<ApiResponse<List<ReadWashingResponse>>> readYetWashing() {
        return ResponseEntity.ok(ApiResponse.ok(washingService.readYetWashing()));
    }

    @GetMapping("/position/{position}")
    public ResponseEntity<ApiResponse<List<ReadWashingResponse>>> findPositionWashing(@PathVariable int position) {
        return ResponseEntity.ok(ApiResponse.ok(washingService.searchPositionWashing(position)));
    }

    @GetMapping("/room/{room}")
    public ResponseEntity<ApiResponse<List<ReadWashingResponse>>> findRoomWashing(@PathVariable String room) {
        return ResponseEntity.ok(ApiResponse.ok(washingService.searchRoomWashing(room)));
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