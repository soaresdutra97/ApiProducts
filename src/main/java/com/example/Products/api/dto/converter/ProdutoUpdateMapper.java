package com.example.Products.api.dto.converter;

import com.example.Products.api.dto.request.ProdutoRequestDTO;
import com.example.Products.business.entities.ProdutoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProdutoUpdateMapper {

    ProdutoUpdateMapper INSTANCE = Mappers.getMapper(ProdutoUpdateMapper.class);

    void updateProductFromDTO(ProdutoRequestDTO produtoRequestDTO, @MappingTarget ProdutoEntity produtoEntity);

}
