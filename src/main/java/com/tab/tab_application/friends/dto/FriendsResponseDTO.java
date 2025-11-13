package com.tab.tab_application.friends.dto;

import com.tab.tab_application.friends.model.FriendStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FriendsResponseDTO {
    private Long id;
    private Long userId;
    private Long friendId;
    private FriendStatus status;
    private LocalDateTime createdAt;
}
