package com.tab.tab_application.friends.repository;

import com.tab.tab_application.friends.model.FriendsModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendsRepository extends JpaRepository<FriendsModel, Long> {
    List<FriendsModel> findUserById(Long userId);
    List<FriendsModel> findFriendById(Long friendId);

}
