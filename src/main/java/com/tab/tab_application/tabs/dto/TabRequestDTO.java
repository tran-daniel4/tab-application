package com.tab.tab_application.tabs.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class TabRequestDTO {
    @NotNull(message = "Name is required")
    private String tabName;

    @NotNull(message = "createdById is required")
    private Long createdById;

    private List<TabMemberRequestDTO> members;

    @DecimalMin(value = "0.00", inclusive = true, message = "tabAmount must be zero or greater")
    private BigDecimal tabAmount;

}
