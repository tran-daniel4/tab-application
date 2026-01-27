package com.tab.tab_application.receipt.dto;

import com.tab.tab_application.receipt.model.ReceiptModel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ReceiptResponseDTO {
    private Long id;
    private List<ReceiptItemResponseDTO> items;
    private BigDecimal tax;
    private BigDecimal tip;
    private BigDecimal total;
    private BigDecimal subtotal;
}
