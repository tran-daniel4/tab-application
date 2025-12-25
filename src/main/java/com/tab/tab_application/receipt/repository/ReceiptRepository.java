package com.tab.tab_application.receipt.repository;

import com.tab.tab_application.receipt.model.ReceiptModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReceiptRepository extends JpaRepository<ReceiptModel, Long> {
    Optional<ReceiptModel> findById(Long id);
    ReceiptModel save(ReceiptModel receipt);
}
