package com.tab.tab_application.tabs.model;

import com.tab.tab_application.user.model.UserModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "tab_invite",
        uniqueConstraints = {
                // prevent multiple pending invites to same user for same tab
                @UniqueConstraint(
                        name = "uq_tab_invite_tab_invitee_status",
                        columnNames = {"tab_id", "invitee_id", "status"}
                )
        },
        indexes = {
                @Index(name = "idx_tab_invite_tab", columnList = "tab_id"),
                @Index(name = "idx_tab_invite_invitee", columnList = "invitee_id")
        }
)
@Getter
@Setter
public class TabInvite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "tab_id", nullable = false)
    private TabModel tab;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "inviter_id", nullable = false)
    private UserModel inviter;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "invitee_id", nullable = false)
    private UserModel invitee;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "sent_at", nullable = false)
    private LocalDateTime sentAt = LocalDateTime.now();

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt = LocalDateTime.now().plusDays(3);

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private InviteStatus status = InviteStatus.PENDING;

    @Column(name = "updated_at",  nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
        if (sentAt == null) sentAt = createdAt;
        if (expiresAt == null) expiresAt = createdAt.plusDays(3);
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
