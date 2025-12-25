package com.tab.tab_application.receipt.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ReceiptItemResponseDTO {
    private Long id;
    private String name;
    private BigDecimal price;

}
