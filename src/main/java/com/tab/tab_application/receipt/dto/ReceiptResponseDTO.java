package com.tab.tab_application.receipt.dto;

import com.tab.tab_application.receipt.model.ReceiptModel;

import java.math.BigDecimal;
import java.util.List;

public class ReceiptResponseDTO {
    private List<ReceiptItemResponseDTO> items;
    private BigDecimal tax;
    private BigDecimal tip;
    private BigDecimal total;
    private BigDecimal subtotal;
}
