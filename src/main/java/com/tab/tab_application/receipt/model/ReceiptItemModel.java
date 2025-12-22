package com.tab.tab_application.receipt.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class ReceiptItemModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @ManyToOne
    private ReceiptModel receipt;
}
