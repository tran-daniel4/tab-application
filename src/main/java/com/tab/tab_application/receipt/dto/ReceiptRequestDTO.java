package com.tab.tab_application.receipt.dto;

import com.tab.tab_application.receipt.model.ReceiptItemModel;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ReceiptRequestDTO {
    @NotEmpty
    private List<ReceiptItemModel> items;

    @NotNull
    @DecimalMin(value = "0.00")
    private BigDecimal tax;

    @NotNull
    @DecimalMin(value = "0.00")
    private BigDecimal tip;

}
