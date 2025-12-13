package com.tab.tab_application.tabs.service;

import com.tab.tab_application.tabs.dto.*;
import com.tab.tab_application.tabs.mapper.TabInviteMapper;
import com.tab.tab_application.tabs.mapper.TabMapper;
import com.tab.tab_application.tabs.model.InviteStatus;
import com.tab.tab_application.tabs.model.TabInvite;
import com.tab.tab_application.tabs.model.TabModel;
import com.tab.tab_application.tabs.repository.TabInviteRepository;
import com.tab.tab_application.tabs.repository.TabRepository;
import com.tab.tab_application.user.model.UserModel;
import com.tab.tab_application.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TabService {
    private final TabRepository tabRepository;
    private final UserRepository userRepository;
    private final TabInviteRepository tabInviteRepository;
    private final TabMapper tabMapper;
    private final TabInviteMapper tabInviteMapper;

    public TabService(TabRepository tabRepository, UserRepository userRepository,
                      TabInviteRepository tabInviteRepository, TabMapper tabMapper,
                      TabInviteMapper tabInviteMapper) {
        this.tabRepository = tabRepository;
        this.userRepository = userRepository;
        this.tabInviteRepository = tabInviteRepository;
        this.tabMapper = tabMapper;
        this.tabInviteMapper = tabInviteMapper;
    }

    @Transactional
    public TabResponseDTO createTab(TabRequestDTO tabRequestDTO) {
        UserModel creator = userRepository.findById(tabRequestDTO.getCreatedById())
                .orElseThrow(() -> new RuntimeException("Creator not found"));

        List<UserModel> memberUsers = new ArrayList<>();
        if (tabRequestDTO.getMembers() != null) {
            for (TabMemberRequestDTO memberRequestDTO: tabRequestDTO.getMembers()) {
                UserModel user = userRepository.findById(memberRequestDTO.getUserId())
                        .orElseThrow(() -> new RuntimeException("User not found: " + memberRequestDTO.getUserId()));
                memberUsers.add(user);
            }
        }

        TabModel tab = tabMapper.toEntity(tabRequestDTO, creator, memberUsers);

        tab = tabRepository.save(tab);

        return tabMapper.toDto(tab);
    }

    @Transactional
    public TabInviteResponseDTO addMemberToTab(Long tabId, Long inviterId, TabInviteRequestDTO tabInviteRequestDTO) {
        TabModel tab = tabRepository.findById(tabId).
                orElseThrow(() -> new IllegalArgumentException("Tab not found"));

        UserModel inviter = userRepository.findById(inviterId).
                orElseThrow(() -> new IllegalArgumentException("Inviter not found"));

        UserModel invitee = userRepository.findById(tabInviteRequestDTO.getInviteeId()).
                orElseThrow(() -> new IllegalArgumentException("Invitee not found"));

        boolean pendingInvite = tabInviteRepository.
                existsByTabAndInviteeAndStatus(tab, invitee, InviteStatus.PENDING);

        boolean acceptedInvite = tabInviteRepository.
                existsByTabAndInviteeAndStatus(tab, invitee, InviteStatus.ACCEPTED);

        boolean notConditionsToInvite = pendingInvite || acceptedInvite;

        if (notConditionsToInvite) {
            throw new IllegalArgumentException("Cannot send invite");
        }

        TabInvite invite = new TabInvite();
        invite.setTab(tab);
        invite.setInviter(inviter);
        invite.setInvitee(invitee);

        invite = tabInviteRepository.save(invite);

        return tabInviteMapper.toDto(invite);

    }

//    public int splitPaymentEvenly() {
//
//    }
//
//    public void setPaymentStatus() {
//
//    }

}
