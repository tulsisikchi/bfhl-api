package com.dypatil.bfhl.controller;

import com.dypatil.bfhl.dto.BfhlRequest;
import com.dypatil.bfhl.dto.BfhlResponse;
import com.dypatil.bfhl.service.BfhlService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
public class BfhlController {

    @Autowired
    private BfhlService bfhlService;

    @PostMapping("/bfhl")
    public ResponseEntity<BfhlResponse> process(
            @RequestHeader(value = "X-Request-Id", required = false) String requestId,
            @Valid @RequestBody BfhlRequest request) {
        long start = System.currentTimeMillis();
        BfhlResponse response = bfhlService.process(request, requestId);
        response.setProcessingTimeMs(System.currentTimeMillis() - start);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP", "message", "BFHL API is running"));
    }
}