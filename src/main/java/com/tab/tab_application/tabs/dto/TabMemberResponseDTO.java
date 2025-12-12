package com.tab.tab_application.tabs.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class TabMemberResponseDTO {
    private Long id;
    private Long userId;
    private BigDecimal amountOwed;
    private boolean hasPaid;
    private List<String> itemsToPay;
}
