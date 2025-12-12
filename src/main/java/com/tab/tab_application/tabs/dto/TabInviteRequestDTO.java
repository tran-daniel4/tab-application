package com.tab.tab_application.tabs.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TabInviteRequestDTO {
    @NotNull(message = "Invitee ID is required")
    private Long inviteeId;

}
