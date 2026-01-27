package com.tab.tab_application.tabs.repository;

import com.tab.tab_application.tabs.model.TabModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TabRepository extends JpaRepository<TabModel, Long> {
    List<TabModel> findByCreatedBy_Id(Long userId);

    @Query("""
        select distinct t
        from TabModel t
        join t.members m
        where m.user.id = :userId
    """)
    List<TabModel> findTabsWhereUserIsMember(@Param("userId") Long userId);

    @Query("""
        select distinct t
        from TabModel t
        left join t.members m
        where t.createdBy.id = :userId or m.user.id = :userId
    """)
    List<TabModel> findTabsForUser(@Param("userId") Long userId);

}
