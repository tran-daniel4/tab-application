package com.tab.tab_application.receipt.controller;

import com.tab.tab_application.receipt.service.ReceiptService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReceiptController {
    private final ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

}
