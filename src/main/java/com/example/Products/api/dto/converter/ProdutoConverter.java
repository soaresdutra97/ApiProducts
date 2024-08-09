package com.example.Products.api.dto.converter;

import org.springframework.stereotype.Component;

@Component
public class ProdutoConverter {

//    public ProdutoEntity toEntity(ProductsDTO dto){
//        return ProdutoEntity.builder()
//                .id(String.valueOf(UUID.randomUUID()))
//                .nome(dto.getNome())
//                .categoria(dto.getCategoria())
//                .descricao(dto.getDescricao())
//                .preco(dto.getPreco())
//                .imagem(dto.getImagem())
//                .dataInclusao(LocalDateTime.now())
//                .build();
//    }
//
//    public ProdutoEntity toEntityUpdate(ProdutoEntity entity, ProductsDTO dto, String id){
//        return ProdutoEntity.builder()
//                .id(id)
//                .nome(dto.getNome() != null ? dto.getNome() : entity.getNome())
//                .categoria(dto.getCategoria() != null ? dto.getCategoria() : entity.getCategoria() )
//                .descricao(dto.getDescricao() != null ? dto.getDescricao() : entity.getDescricao())
//                .preco(dto.getPreco() != null ? dto.getPreco() :entity.getPreco())
//                .imagem(dto.getImagem() !=null ? dto.getImagem() : entity.getImagem())
//                .dataInclusao(entity.getDataInclusao())
//                .dataAtualizacao(LocalDateTime.now())
//                .build();
//    }
//
//    public ProductsDTO toDTO(ProdutoEntity entity){
//        return ProductsDTO.builder()
//                .entityId(entity.getId())
//                .nome(entity.getNome())
//                .categoria(entity.getCategoria())
//                .descricao(entity.getDescricao())
//                .preco(entity.getPreco())
//                .imagem(entity.getImagem())
//                .build();
//    }

//    public List<ProductsDTO> toListDTO(List<ProdutoEntity> entityList){
//        return entityList.stream().map(this::toDTO).toList();
//    }

}
