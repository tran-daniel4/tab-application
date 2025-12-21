package com.tab.tab_application.tabs.mapper;

import com.tab.tab_application.tabs.dto.TabMemberResponseDTO;
import com.tab.tab_application.tabs.model.TabMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TabMemberMapper {
    @Mapping(source = "user.id", target = "userId")
    TabMemberResponseDTO toDto(TabMember tabMember);

    List<TabMemberResponseDTO> toDtoList(List<TabMember> members);


}
