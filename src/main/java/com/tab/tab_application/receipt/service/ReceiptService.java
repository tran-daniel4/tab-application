package com.tab.tab_application.receipt.service;

import com.tab.tab_application.receipt.dto.ReceiptItemRequestDTO;
import com.tab.tab_application.receipt.dto.ReceiptItemResponseDTO;
import com.tab.tab_application.receipt.dto.ReceiptRequestDTO;
import com.tab.tab_application.receipt.dto.ReceiptResponseDTO;
import com.tab.tab_application.receipt.mapper.ReceiptItemMapper;
import com.tab.tab_application.receipt.mapper.ReceiptMapper;
import com.tab.tab_application.receipt.model.ReceiptItemModel;
import com.tab.tab_application.receipt.model.ReceiptModel;
import com.tab.tab_application.receipt.repository.ReceiptRepository;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class ReceiptService {
    public final ReceiptMapper receiptMapper;
    public final ReceiptItemMapper receiptItemMapper;
    public final ReceiptRepository receiptRepository;

    public ReceiptService(ReceiptMapper receiptMapper, ReceiptItemMapper receiptItemMapper, ReceiptRepository receiptRepository) {
        this.receiptMapper = receiptMapper;
        this.receiptItemMapper = receiptItemMapper;
        this.receiptRepository = receiptRepository;
    }

    @Transactional
    public ReceiptResponseDTO createReceipt(ReceiptRequestDTO request) {
        ReceiptModel receipt = new ReceiptModel();
        receipt.setTax(
                request.getTax() != null ? request.getTax() : BigDecimal.ZERO
        );
        receipt.setTip(
                request.getTip() != null ? request.getTip() : BigDecimal.ZERO
        );

        List<ReceiptItemModel> items = request.getItems().stream()
                .map(ReceiptItemRequestDTO -> {
                    ReceiptItemModel item = new ReceiptItemModel();
                    item.setName(ReceiptItemRequestDTO.getName());
                    item.setPrice(ReceiptItemRequestDTO.getPrice());
                    item.setReceipt(ReceiptItemRequestDTO.getReceipt());
                    return item;
                })
                .toList();

        receipt.setItems(items);
        calculateTotals(receipt);
        receiptRepository.save(receipt);
        return receiptMapper.toDTO(receipt);

    }

    @Transactional
    public ReceiptResponseDTO getReceiptById(Long receiptId) {
        ReceiptModel receipt = receiptRepository.findById(receiptId)
                .orElseThrow(() -> new IllegalArgumentException("Receipt not found"));
        return receiptMapper.toDTO(receipt);
    }

//    ReceiptResponseDTO updateReceipt(Long receiptId, ReceiptRequestDTO request) {
//        ReceiptModel receipt = receiptRepository.findById(receiptId)
//                .orElseThrow(() -> new IllegalArgumentException("Receipt not found"));
//
//        receipt.setTax(request.getTax());
//        receipt.setTip(request.getTip());
//
//        receipt.getItems().clear();
//        for (ReceiptItemRequestDTO itemDto : request.getItems()) {
//            ReceiptItemModel item = receiptItemMapper.toEntity(itemDto);
//            item.setReceipt(receipt);
//            receipt.getItems().add(item);
//        }
//        calculateTotals(receipt);
//
//        receipt.getTab().setTabAmount(receipt.getTotal());
//
//        return receiptMapper.toDto(receipt);
//
//    }

    public void calculateTotals(ReceiptModel receipt) {
        BigDecimal subtotal = receipt.getItems().stream()
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
//
//    public ReceiptResponseDTO createReceiptFromOcr(OcrReceiptRequestDTO request) {
//
//    }
//
//    public ReceiptResponseDTO applyOcrResults(Long receiptId, OcrReceiptRequestDTO request) {
//
//    }
//
//    public void validateReceipt(Receipt receipt) {
//
//    }


}
