package com.tab.tab_application.friends.controller;

import com.tab.tab_application.friends.dto.FriendsResponseDTO;
import com.tab.tab_application.friends.service.FriendsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
public class FriendsController {
    public final FriendsService friendsService;

    public FriendsController (FriendsService friendsService) {
        this.friendsService = friendsService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<FriendsResponseDTO>> getAllFriends(@PathVariable Long id) {
        return ResponseEntity.ok(friendsService.getFriendsList(id));
    }


}
