package com.tab.tab_application.friends.service;

import com.tab.tab_application.friends.dto.FriendsResponseDTO;
import com.tab.tab_application.friends.mapper.FriendsMapper;
import com.tab.tab_application.friends.model.FriendStatus;
import com.tab.tab_application.friends.model.FriendsModel;
import com.tab.tab_application.friends.repository.FriendsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FriendsService {
    private final FriendsRepository friendsRepository;
    private final FriendsMapper friendsMapper;

    public FriendsService(FriendsRepository friendsRepository, FriendsMapper friendsMapper) {
        this.friendsRepository = friendsRepository;
        this.friendsMapper = friendsMapper;
    }

    public List<FriendsResponseDTO> getFriendsList(Long userId) {
        List<FriendsModel> sentRequests = friendsRepository.findUserById(userId);
        List<FriendsModel> receivedRequests = friendsRepository.findFriendById(userId);

        List<FriendsModel> allFriends = new ArrayList<>();
        allFriends.addAll(sentRequests);
        allFriends.addAll(receivedRequests);

        return allFriends.stream()
                .filter(friendsModel -> friendsModel.getStatus() == FriendStatus.ACCEPTED)
                .map(friendsMapper::toDto)
                .collect(Collectors.toList());
    }

}
