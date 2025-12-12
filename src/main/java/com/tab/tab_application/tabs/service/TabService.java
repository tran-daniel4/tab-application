package com.tab.tab_application.tabs.service;

import com.tab.tab_application.tabs.dto.TabMemberRequestDTO;
import com.tab.tab_application.tabs.dto.TabRequestDTO;
import com.tab.tab_application.tabs.dto.TabResponseDTO;
import com.tab.tab_application.tabs.mapper.TabMapper;
import com.tab.tab_application.tabs.model.TabModel;
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
    private final TabMapper tabMapper;

    public TabService(TabRepository tabRepository, UserRepository userRepository, TabMapper tabMapper) {
        this.tabRepository = tabRepository;
        this.userRepository = userRepository;
        this.tabMapper = tabMapper;
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

//    public TabMemberResponseDTO addMemberToTab() {
//
//    }
//
//    public int splitPaymentEvenly() {
//
//    }
//
//    public void setPaymentStatus() {
//
//    }

}
