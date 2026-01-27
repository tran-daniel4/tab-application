package com.tab.tab_application.tabs.mapper;

import com.tab.tab_application.receipt.model.ReceiptModel;
import com.tab.tab_application.tabs.dto.TabMemberRequestDTO;
import com.tab.tab_application.tabs.dto.TabMemberResponseDTO;
import com.tab.tab_application.tabs.dto.TabRequestDTO;
import com.tab.tab_application.tabs.dto.TabResponseDTO;
import com.tab.tab_application.tabs.model.TabMember;
import com.tab.tab_application.tabs.model.TabModel;
import com.tab.tab_application.user.model.UserModel;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class TabMapper {
    public TabResponseDTO toDto(TabModel tab) {
        if (tab == null) return null;

        TabResponseDTO dto = new TabResponseDTO();
        dto.setId(tab.getId());
        dto.setTabName(tab.getTabName());
        dto.setCreatedById(tab.getCreatedBy().getId());
        dto.setDateCreated(tab.getDateCreated());
        dto.setTabAmount(tab.getTabAmount());

        List<TabMemberResponseDTO> memberResponses = new ArrayList<>();
        if (tab.getMembers() != null) {
            for (TabMember member: tab.getMembers()) {
                TabMemberResponseDTO memberResponse = new TabMemberResponseDTO();
                memberResponse.setId(member.getId());
                memberResponse.setUserId(member.getUser().getId());
                memberResponse.setAmountOwed(member.getAmountOwed());
                memberResponse.setHasPaid(member.isHasPaid());
                memberResponse.setItemsToPay(member.getItemsToPay());

                memberResponses.add(memberResponse);
            }
        }
        dto.setMembers(memberResponses);
        return dto;
    }

    public TabModel toEntity(TabRequestDTO dto, UserModel createdBy, List<UserModel> memberUsers) {
        TabModel tab = new TabModel();
        tab.setTabName(dto.getTabName());
        tab.setCreatedBy(createdBy);
        tab.setDateCreated(LocalDateTime.now());
        tab.setTabAmount(dto.getTabAmount());

        List<TabMember> members = new ArrayList<>();
        if (dto.getMembers() != null) {
            for (int i = 0; i < dto.getMembers().size(); i++) {
                TabMemberRequestDTO memberDto = dto.getMembers().get(i);
                UserModel user = memberUsers.get(i);

                TabMember member = new TabMember();
                member.setUser(user);
                member.setAmountOwed(memberDto.getAmountOwed());
                member.setHasPaid(memberDto.isHasPaid());
                member.setItemsToPay(memberDto.getItemsToPay());
                member.setTab(tab);

                members.add(member);
            }
        }
        tab.setMembers(members);
        return tab;
    }

}
