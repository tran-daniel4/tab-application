package com.tab.tab_application.tabs.repository;

import com.tab.tab_application.tabs.model.TabModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TabRepository extends JpaRepository<TabModel, Long> {
}
