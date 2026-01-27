package com.tab.tab_application.tabs.model;

import com.tab.tab_application.receipt.model.ReceiptModel;
import com.tab.tab_application.user.model.UserModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
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

    @OneToMany(mappedBy = "tab", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TabMember> members;

    @Column(precision = 19, scale = 2)
    private BigDecimal tabAmount;

    @OneToOne(mappedBy = "tab", cascade = CascadeType.ALL)
    private ReceiptModel receipt;

}
