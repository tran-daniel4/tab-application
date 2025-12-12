package com.tab.tab_application.tabs.mapper;

import com.tab.tab_application.tabs.dto.TabInviteRequestDTO;
import com.tab.tab_application.tabs.dto.TabInviteResponseDTO;
import com.tab.tab_application.tabs.model.TabInvite;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TabInviteMapper {
    @Mapping(source = "tab.id", target = "tabId")
    @Mapping(source = "inviter.id", target = "inviterId")
    @Mapping(source = "invitee.id", target = "inviteeId")
    TabInviteResponseDTO toDto(TabInvite invite);

}
