package com.tab.tab_application.receipt.service;

import com.tab.tab_application.observability.metrics.BusinessMetrics;
import com.tab.tab_application.receipt.dto.ReceiptItemRequestDTO;
import com.tab.tab_application.receipt.dto.ReceiptRequestDTO;
import com.tab.tab_application.receipt.dto.ReceiptResponseDTO;
import com.tab.tab_application.receipt.mapper.ReceiptItemMapper;
import com.tab.tab_application.receipt.mapper.ReceiptMapper;
import com.tab.tab_application.receipt.model.ReceiptItemModel;
import com.tab.tab_application.receipt.model.ReceiptModel;
import com.tab.tab_application.receipt.repository.ReceiptRepository;
import com.tab.tab_application.tabs.model.TabModel;
import com.tab.tab_application.tabs.repository.TabRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class ReceiptService {
    public final ReceiptMapper receiptMapper;
    public final ReceiptItemMapper receiptItemMapper;
    public final ReceiptRepository receiptRepository;
    public final TabRepository tabRepository;
    public final BusinessMetrics businessMetrics;

    public ReceiptService(ReceiptMapper receiptMapper, ReceiptItemMapper receiptItemMapper,
                          ReceiptRepository receiptRepository, TabRepository tabRepository,
                          BusinessMetrics businessMetrics) {
        this.receiptMapper = receiptMapper;
        this.receiptItemMapper = receiptItemMapper;
        this.receiptRepository = receiptRepository;
        this.tabRepository = tabRepository;
        this.businessMetrics = businessMetrics;
    }

    @Transactional
    public ReceiptResponseDTO createReceipt(Long tabId, ReceiptRequestDTO request) {
        validateReceipt(request);

        TabModel tab = tabRepository.findById(tabId)
                .orElseThrow(() -> new IllegalArgumentException("Tab not found"));

        ReceiptModel receipt = new ReceiptModel();
        receipt.setTab(tab);

        receipt.setTax(request.getTax() != null ? request.getTax() : BigDecimal.ZERO);
        receipt.setTip(request.getTip() != null ? request.getTip() : BigDecimal.ZERO);

        List<ReceiptItemModel> items = request.getItems().stream()
                .map(itemDto -> {
                    ReceiptItemModel item = receiptItemMapper.toEntity(itemDto);
                    item.setReceipt(receipt);
                    return item;
                })
                .toList();

        receipt.setItems(items);

        calculateTotals(receipt);

        ReceiptModel saved = receiptRepository.save(receipt);
        businessMetrics.receiptCreated();
        syncTabAmount(tabId);

        return receiptMapper.toDTO(saved);
    }

    @Transactional
    public ReceiptResponseDTO getReceiptById(Long receiptId) {
        ReceiptModel receipt = receiptRepository.findById(receiptId)
                .orElseThrow(() -> new IllegalArgumentException("Receipt not found"));
        return receiptMapper.toDTO(receipt);
    }

    @Transactional
    public List<ReceiptResponseDTO> getReceiptsForTab(Long tabId) {
        tabRepository.findById(tabId)
                .orElseThrow(() -> new IllegalArgumentException("Tab not found"));

        return receiptRepository.findByTab_Id(tabId).stream()
                .map(receiptMapper::toDTO)
                .toList();
    }

    @Transactional
    public ReceiptResponseDTO updateReceipt(Long receiptId, ReceiptRequestDTO request) {
        ReceiptModel receipt = receiptRepository.findById(receiptId)
                .orElseThrow(() -> new IllegalArgumentException("Receipt not found"));

        receipt.setTax(request.getTax() != null ? request.getTax() : BigDecimal.ZERO);
        receipt.setTip(request.getTip() != null ? request.getTip() : BigDecimal.ZERO);

        receipt.getItems().clear();
        for (ReceiptItemRequestDTO itemDto : request.getItems()) {
            ReceiptItemModel item = receiptItemMapper.toEntity(itemDto);
            item.setReceipt(receipt);
            receipt.getItems().add(item);
        }
        calculateTotals(receipt);

        ReceiptModel updated = receiptRepository.save(receipt);
        syncTabAmount(updated.getTab().getId());
        return receiptMapper.toDTO(updated);

    }

    public void calculateTotals(ReceiptModel receipt) {
        BigDecimal subtotal = receipt.getItems() == null
                ? BigDecimal.ZERO
                : receipt.getItems().stream()
                .map((ReceiptItemModel::getPrice))
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        receipt.setSubtotal(subtotal);

        BigDecimal tax = receipt.getTax() != null
                ? receipt.getTax() : BigDecimal.ZERO;

        BigDecimal tip = receipt.getTip() != null
                ? receipt.getTip() : BigDecimal.ZERO;

        receipt.setTax(tax);
        receipt.setTip(tip);
        receipt.setTotal(subtotal.add(tax).add(tip));
    }

    @Transactional
    public void syncTabAmount(Long tabId) {
        TabModel tab = tabRepository.findById(tabId)
                .orElseThrow(() -> new IllegalArgumentException("Tab not found"));

        BigDecimal sum = receiptRepository.findByTab_Id(tabId).stream()
                .map(ReceiptModel::getTotal)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        tab.setTabAmount(sum);
        tabRepository.save(tab);
    }

//
//    public ReceiptResponseDTO createReceiptFromOcr(OcrReceiptRequestDTO request) {
//
//    }
//
//    public ReceiptResponseDTO applyOcrResults(Long receiptId, OcrReceiptRequestDTO request) {
//
//    }
//
    public void validateReceipt(ReceiptRequestDTO requestDTO) {
        if (requestDTO == null) {
            throw new IllegalArgumentException("Receipt request cannot be null");
        }

        if (requestDTO.getItems() == null || requestDTO.getItems().isEmpty()) {
            throw new IllegalArgumentException("Receipt must contain at least one item");
        }

        // tax/tip are allowed to be null (you default them), but if present must be >= 0
        if (requestDTO.getTax() != null && requestDTO.getTax().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Tax cannot be negative");
        }
        if (requestDTO.getTip() != null && requestDTO.getTip().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Tip cannot be negative");
        }

        for (int i = 0; i < requestDTO.getItems().size(); i++) {
            ReceiptItemRequestDTO item = requestDTO.getItems().get(i);

            if (item == null) {
                throw new IllegalArgumentException("Receipt item at index " + i + " cannot be null");
            }

            String name = item.getName();
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Item name is required (index " + i + ")");
            }

            BigDecimal price = item.getPrice();
            if (price == null) {
                throw new IllegalArgumentException("Item price is required (index " + i + ")");
            }
            if (price.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Item price must be > 0 (index " + i + ")");
            }
        }
    }


}
