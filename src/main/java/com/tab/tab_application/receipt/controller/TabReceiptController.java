package com.tab.tab_application.receipt.controller;

import com.tab.tab_application.receipt.dto.ReceiptRequestDTO;
import com.tab.tab_application.receipt.dto.ReceiptResponseDTO;
import com.tab.tab_application.receipt.service.ReceiptService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/tabs")
public class TabReceiptController {

    private final ReceiptService receiptService;

    public TabReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @PostMapping("/{tabId}/receipt")
    public ResponseEntity<ReceiptResponseDTO> createReceiptForTab(
            @PathVariable Long tabId,
            @Valid @RequestBody ReceiptRequestDTO request
    ) {
        ReceiptResponseDTO created = receiptService.createReceipt(tabId, request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/receipts/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(location).body(created);
    }
}
