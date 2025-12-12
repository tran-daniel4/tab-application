package com.tab.tab_application.tabs.repository;

import com.tab.tab_application.tabs.model.InviteStatus;
import com.tab.tab_application.tabs.model.TabInvite;
import com.tab.tab_application.tabs.model.TabModel;
import com.tab.tab_application.user.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TabInviteRepository extends JpaRepository<TabInvite, Long> {
    Optional<TabInvite> findByTabAndInviteeAndStatus(TabModel tabModel, UserModel invitee, InviteStatus status);
    List<TabInvite> findByInvitee(UserModel invitee);
    List<TabInvite> findByTab(TabModel tab);
    List<TabInvite> findByStatusAndExpiresAtBefore(InviteStatus status, LocalDateTime now);
}
