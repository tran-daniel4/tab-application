package com.tab.tab_application.receipt.mapper;

import com.tab.tab_application.receipt.dto.ReceiptItemRequestDTO;
import com.tab.tab_application.receipt.dto.ReceiptItemResponseDTO;
import com.tab.tab_application.receipt.model.ReceiptItemModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReceiptItemMapper {
    ReceiptItemModel toEntity(ReceiptItemRequestDTO dto);
    ReceiptItemResponseDTO toDto(ReceiptItemModel model);
}
