package com.tab.tab_application.receipt.mapper;


import com.tab.tab_application.receipt.dto.ReceiptResponseDTO;
import com.tab.tab_application.receipt.model.ReceiptModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = ReceiptItemMapper.class)
public interface ReceiptMapper {
    ReceiptResponseDTO toDTO(ReceiptModel receipt);

}
