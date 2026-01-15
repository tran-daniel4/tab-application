package com.tab.tab_application.receipt.controller;

import com.tab.tab_application.receipt.dto.ReceiptRequestDTO;
import com.tab.tab_application.receipt.dto.ReceiptResponseDTO;
import com.tab.tab_application.receipt.service.ReceiptService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/receipt")
public class ReceiptController {
    private final ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @PostMapping
    public ResponseEntity<ReceiptResponseDTO> createReceipt(@Valid @RequestBody ReceiptRequestDTO request) {
        ReceiptResponseDTO created = receiptService.createReceipt(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReceiptResponseDTO> getReceiptById(@PathVariable Long id) {
        ReceiptResponseDTO receipt = receiptService.getReceiptById(id);
        return ResponseEntity.ok(receipt);
    }

    @PostMapping
    public ResponseEntity<ReceiptResponseDTO> updateReceipt(@PathVariable Long id, @Valid @RequestBody ReceiptRequestDTO request) {
        ReceiptResponseDTO updated = receiptService.updateReceipt(id, request);
        return ResponseEntity.ok(updated);
    }



}
