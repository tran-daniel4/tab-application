package com.tab.tab_application.tabs.model;

import com.tab.tab_application.user.model.UserModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class TabMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserModel user;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amountOwed;

    private boolean hasPaid;

    @ElementCollection
    private List<String> itemsToPay = new ArrayList<>();

    @ManyToOne
    private TabModel tab;
}
