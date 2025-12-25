package com.tab.tab_application.receipt.mapper;

import com.tab.tab_application.receipt.dto.ReceiptItemRequestDTO;
import com.tab.tab_application.receipt.dto.ReceiptItemResponseDTO;
import com.tab.tab_application.receipt.model.ReceiptItemModel;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
public class ReceiptItemMapper {

    public ReceiptItemModel toEntity(ReceiptItemRequestDTO dto) {
        ReceiptItemModel item = new ReceiptItemModel();
        item.setName(dto.getName());
        item.setPrice(dto.getPrice());
        return item;
    }

    public ReceiptItemResponseDTO toDto(ReceiptItemModel model) {
        ReceiptItemResponseDTO dto = new ReceiptItemResponseDTO();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setPrice(model.getPrice());
        return dto;
    }
}
