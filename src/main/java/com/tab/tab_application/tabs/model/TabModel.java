package com.tab.tab_application.tabs.model;

import com.tab.tab_application.user.model.UserModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tab")
@Getter
@Setter
public class TabModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String tabName;

    @ManyToOne
    private UserModel createdBy;

    @Column
    private LocalDateTime dateCreated;

    @Column
    @OneToMany(mappedBy = "tab", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TabMember> members;

}
