package com.tab.tab_application.friends.model;

import com.tab.tab_application.user.model.UserModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "friends")
@Getter
@Setter
public class FriendsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

    @ManyToOne
    @JoinColumn(name = "friend_id")
    private UserModel friend;

    @Enumerated(EnumType.STRING)
    private FriendStatus status;

    private LocalDateTime createdAt = LocalDateTime.now();

}
