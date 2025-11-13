package com.tab.tab_application.friends.mapper;

import com.tab.tab_application.friends.dto.FriendsRequestDTO;
import com.tab.tab_application.friends.dto.FriendsResponseDTO;
import com.tab.tab_application.friends.model.FriendsModel;
import com.tab.tab_application.user.model.UserModel;
import com.tab.tab_application.user.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class FriendsMapper {
    private final UserRepository userRepository;

    public FriendsMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public FriendsResponseDTO toDto(FriendsModel friend) {
        if (friend == null) return null;

        FriendsResponseDTO dto = new FriendsResponseDTO();
        dto.setId(friend.getId());
        dto.setUserId(friend.getUser().getId());
        dto.setFriendId(friend.getFriend().getId());
        dto.setStatus(friend.getStatus());
        dto.setCreatedAt(LocalDateTime.now());
        return dto;
    }

    public FriendsModel toEntity(FriendsRequestDTO dto) {
        if (dto == null) return null;

        FriendsModel friend = new FriendsModel();
        friend.setUser(getUserById(dto.getUserId()));
        friend.setFriend(getUserById(dto.getFriendId()));
        friend.setStatus(dto.getStatus());
        return friend;
    }

    private UserModel getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " +id));
    }
}
