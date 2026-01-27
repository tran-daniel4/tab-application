package com.tab.tab_application.tabs.dto;

import com.tab.tab_application.tabs.model.InviteStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TabInviteResponseDTO {
    private Long id;
    private Long tabId;
    private Long inviterId;
    private Long inviteeId;
    private String message;
    private InviteStatus status;
    private LocalDateTime sentAt;
    private LocalDateTime expiresAt;

}
