package com.example.Products.api.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class ProdutoRequestDTO {

    private String nome;
    private BigDecimal preco;
    private String categoria;
    private String descricao;
    private String imagem;

}