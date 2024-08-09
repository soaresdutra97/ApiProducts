package com.example.Products.business.converter;

import com.example.Products.api.dto.converter.ProdutoConverter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class ProdutoConverterTest {

    @InjectMocks
    ProdutoConverter converter;
//
//    @Test
//    void deveConverterParaProdutoEntityComSucesso(){
//        ProductsDTO produtoDTO = ProductsDTO.builder()
//                .nome("Jaqueta vermelha")
//                .categoria("Roupas")
//                .descricao("Jaqueta vermalha com bolsos")
//                .preco(new BigDecimal("500.00"))
//                .build();
//
//        ProdutoEntity produtoEntityEsperado = ProdutoEntity.builder()
//                .id("1234")
//                .nome("Jaqueta vermelha")
//                .categoria("Roupas")
//                .descricao("Jaqueta vermalha com bolsos")
//                .preco(new BigDecimal("500.00"))
//                .build();
//
//        ProdutoEntity produtoEntity = converter.toEntity(produtoDTO);
//
//        assertEquals(produtoEntityEsperado.getNome(), produtoEntity.getNome());
//        assertEquals(produtoEntityEsperado.getCategoria(), produtoEntity.getCategoria());
//        assertEquals(produtoEntityEsperado.getDescricao(), produtoEntity.getDescricao());
//        assertEquals(produtoEntityEsperado.getPreco(), produtoEntity.getPreco());
//        assertEquals(produtoEntityEsperado.getImagem(), produtoEntity.getImagem());
//
//        assertNotNull(produtoEntity.getId());
//        assertNotNull(produtoEntity.getDataInclusao());
//    }
//
//    @Test
//    void deveConverterParaProdutoEntityUpdateComSucesso(){
//        ProductsDTO produtoDTO = ProductsDTO.builder()
//                .descricao("Jaqueta vermalha com bolsos laterais e listras")
//                .preco(new BigDecimal("250.00"))
//                .build();
//
//        String id = "1234";
//
//        ProdutoEntity produtoEntityEsperado = ProdutoEntity.builder()
//                .id("1234")
//                .nome("Jaqueta vermelha")
//                .categoria("Roupas")
//                .descricao("Jaqueta vermalha com bolsos laterais e listras")
//                .preco(new BigDecimal("250.00"))
//                .build();
//
//        ProdutoEntity entity = ProdutoEntity.builder()
//                .id("1234")
//                .nome("Jaqueta vermelha")
//                .categoria("Roupas")
//                .descricao("Jaqueta vermalha com bolsos")
//                .preco(new BigDecimal("500.00"))
//                .build();
//
//        ProdutoEntity produtoEntity = converter.toEntityUpdate(entity, produtoDTO, id);
//
//        assertEquals(produtoEntityEsperado.getNome(), produtoEntity.getNome());
//        assertEquals(produtoEntityEsperado.getCategoria(), produtoEntity.getCategoria());
//        assertEquals(produtoEntityEsperado.getDescricao(), produtoEntity.getDescricao());
//        assertEquals(produtoEntityEsperado.getPreco(), produtoEntity.getPreco());
//        assertEquals(produtoEntityEsperado.getImagem(), produtoEntity.getImagem());
//        assertEquals(produtoEntityEsperado.getId(), produtoEntity.getId());
//        assertEquals(produtoEntityEsperado.getDataInclusao(), produtoEntity.getDataInclusao());
//
//        assertNotNull(produtoEntity.getDataAtualizacao());
//    }
//
//
//    @Test
//    void deveConverterParaProductDTOComSucesso(){
//        ProductsDTO produtoDtoEsperado = ProductsDTO.builder()
//                .nome("Jaqueta vermelha")
//                .categoria("Roupas")
//                .descricao("Jaqueta vermalha com bolsos")
//                .preco(new BigDecimal("500.00"))
//                .build();
//
//        ProdutoEntity produtoEntity = ProdutoEntity.builder()
//                .id("1234")
//                .nome("Jaqueta vermelha")
//                .categoria("Roupas")
//                .descricao("Jaqueta vermalha com bolsos")
//                .preco(new BigDecimal("500.00"))
//                .build();
//
//        ProductsDTO productsDTO = converter.toDTO(produtoEntity);
//
//        assertEquals(produtoDtoEsperado.getNome(), productsDTO.getNome());
//        assertEquals(produtoDtoEsperado.getCategoria(), productsDTO.getCategoria());
//        assertEquals(produtoDtoEsperado.getDescricao(), productsDTO.getDescricao());
//        assertEquals(produtoDtoEsperado.getPreco(), productsDTO.getPreco());
//        assertEquals(produtoDtoEsperado.getImagem(), productsDTO.getImagem());
//
//        //assertNotNull(productsDTO.getId());
//        //assertNotNull(productsDTO.getDataInclusao());
//    }
//
//
//    @Test
//    void deveConverterParaListaProductDTOComSucesso(){
//
//        List<ProductsDTO> listaProdutosDTO = new ArrayList<>();
//        List<ProdutoEntity> listaProdutosEntity= new ArrayList<>();
//
//        ProductsDTO produtoDTO = ProductsDTO.builder()
//                .entityId("1234")
//                .nome("Jaqueta vermelha")
//                .categoria("Roupas")
//                .descricao("Jaqueta vermalha com bolsos laterais e listras")
//                .preco(new BigDecimal("250.00"))
//                .build();
//
//        listaProdutosDTO.add(produtoDTO);
//
//        ProdutoEntity produtoEntityEsperado = ProdutoEntity.builder()
//                .id("1234")
//                .nome("Jaqueta vermelha")
//                .categoria("Roupas")
//                .descricao("Jaqueta vermalha com bolsos laterais e listras")
//                .preco(new BigDecimal("250.00"))
//                .build();
//
//        listaProdutosEntity.add(produtoEntityEsperado);
//
//        List<ProductsDTO> produtoEntity = converter.toListDTO(listaProdutosEntity);
//
//        assertEquals(listaProdutosDTO, produtoEntity);
//
//    }



}
