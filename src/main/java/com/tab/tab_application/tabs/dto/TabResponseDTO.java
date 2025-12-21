package com.tab.tab_application.tabs.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class TabResponseDTO {
    private Long id;
    private String tabName;
    private Long createdById;
    private LocalDateTime dateCreated;
    private List<TabMemberResponseDTO> members;
    private BigDecimal tabAmount;
}
