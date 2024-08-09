package com.example.Products.api.dto.converter;

import com.example.Products.api.dto.request.ProdutoRequestDTO;
import com.example.Products.api.dto.response.ProdutoResponseDTO;
import com.example.Products.business.entities.ProdutoEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    ProdutoEntity converteDTOParaEntity(ProdutoRequestDTO produtoRequestDTO);

    ProdutoResponseDTO converteEntityParaDto(ProdutoEntity produtoEntity);
    List<ProdutoResponseDTO> converteEntityParaDtoList(List<ProdutoEntity> produtoEntities);

}
