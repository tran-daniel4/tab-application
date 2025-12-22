package com.tab.tab_application.receipt.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class ReceiptItemRequestDTO {
    @NotBlank
    private String name;

    @NotNull
    @DecimalMin(value = "0.00", inclusive = false)
    private BigDecimal price;
}
