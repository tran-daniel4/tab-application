package com.tab.tab_application.tabs.controller;

import com.tab.tab_application.tabs.dto.TabInviteRequestDTO;
import com.tab.tab_application.tabs.dto.TabInviteResponseDTO;
import com.tab.tab_application.tabs.dto.TabRequestDTO;
import com.tab.tab_application.tabs.dto.TabResponseDTO;
import com.tab.tab_application.tabs.service.TabService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<TabResponseDTO>> getTabsForUser(@RequestParam Long userId) {
        return ResponseEntity.ok(tabService.getTabsForUser(userId));
    }

    @GetMapping("/{tabId}")
    public ResponseEntity<TabResponseDTO> getTabById(@PathVariable Long tabId) {
        return ResponseEntity.ok(tabService.getTabById(tabId));
    }


    @PostMapping("/{tabId}/invite")
    public ResponseEntity<TabInviteResponseDTO> inviteToTab(
            @PathVariable Long tabId,
            @RequestParam Long inviterId,
            @Valid @RequestBody TabInviteRequestDTO requestDTO
    ) {
        TabInviteResponseDTO response =
                tabService.addMemberToTab(tabId, inviterId, requestDTO);

        return ResponseEntity.ok(response);
    }



}
