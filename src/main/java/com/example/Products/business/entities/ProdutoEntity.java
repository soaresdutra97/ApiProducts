package com.example.Products.business.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "ProdutoEntity")
@Table(name = "estoque")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProdutoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "title", length = 800)
    private String nome;
    @Column(name = "price")
    private BigDecimal preco;
    @Column(name = "category", length = 800)
    private String categoria;
    @Column(name = "description", length = 800)
    private String descricao;
    @Column(name = "image", length = 800)
    private String imagem;
    @Column(name = "data_inclusao")
    private LocalDateTime dataInclusao;
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;
}