package com.tab.tab_application.friends.dto;

import com.tab.tab_application.friends.model.FriendStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendsRequestDTO {
    @NotNull(message = "UserId is required")
    private Long userId;

    @NotNull(message = "FriendId is required")
    private Long friendId;

    private FriendStatus status = FriendStatus.PENDING;

}
