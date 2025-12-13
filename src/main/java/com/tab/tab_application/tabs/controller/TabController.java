package com.tab.tab_application.tabs.controller;

import com.tab.tab_application.tabs.dto.TabInviteRequestDTO;
import com.tab.tab_application.tabs.dto.TabInviteResponseDTO;
import com.tab.tab_application.tabs.dto.TabRequestDTO;
import com.tab.tab_application.tabs.dto.TabResponseDTO;
import com.tab.tab_application.tabs.service.TabService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tabs")
public class TabController {
    private final TabService tabService;


    public TabController(TabService tabService) {
        this.tabService = tabService;
    }

    @PostMapping("/create")
    public ResponseEntity<TabResponseDTO> createTab(@RequestBody TabRequestDTO tabRequestDTO) {
        TabResponseDTO response = tabService.createTab(tabRequestDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{tabId}/invite")
    public ResponseEntity<TabInviteResponseDTO> inviteToTab(
            @PathVariable Long tabId,
            @RequestParam Long inviterId, // this should not be in production
            @RequestBody TabInviteRequestDTO requestDTO
    ) {
        TabInviteResponseDTO response =
                tabService.addMemberToTab(tabId, inviterId, requestDTO);

        return ResponseEntity.ok(response);
    }

}
