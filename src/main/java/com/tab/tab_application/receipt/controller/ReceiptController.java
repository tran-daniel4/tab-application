package com.tab.tab_application.receipt.controller;

import com.tab.tab_application.receipt.dto.ReceiptRequestDTO;
import com.tab.tab_application.receipt.dto.ReceiptResponseDTO;
import com.tab.tab_application.receipt.service.ReceiptService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/receipts")
public class ReceiptController {

    private final ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReceiptResponseDTO> getReceiptById(@PathVariable Long id) {
        return ResponseEntity.ok(receiptService.getReceiptById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReceiptResponseDTO> updateReceipt(
            @PathVariable Long id,
            @Valid @RequestBody ReceiptRequestDTO request
    ) {
        return ResponseEntity.ok(receiptService.updateReceipt(id, request));
    }
}
