package com.example.Products.business.service;

import com.example.Products.api.dto.converter.ProdutoConverter;
import com.example.Products.infrastructure.client.FakeApiClient;
import com.example.Products.business.entities.ProdutoEntity;
import com.example.Products.infrastructure.exceptions.BusinessException;
import com.example.Products.infrastructure.exceptions.ConflictException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class FakeApiServiceTest {

    @InjectMocks
    FakeApiService service;
    @Mock
    FakeApiClient client;
    @Mock
    ProdutoConverter converter;
    @Mock
    ProdutoService produtoService;

    @Test
    void deveBuscarProdutosEGravarComSucesso(){

        List<ProductsDTO> listaProdutosDTO = new ArrayList<>();

        ProductsDTO produtoDTO = ProductsDTO.builder()
                .entityId("1234")
                .nome("Jaqueta vermelha")
                .categoria("Roupas")
                .descricao("Jaqueta vermalha com bolsos laterais e listras")
                .preco(new BigDecimal("250.00"))
                .build();

        listaProdutosDTO.add(produtoDTO);

        ProdutoEntity produtoEntity = ProdutoEntity.builder()
                .id("1234")
                .nome("Jaqueta vermelha")
                .categoria("Roupas")
                .descricao("Jaqueta vermalha com bolsos laterais e listras")
                .preco(new BigDecimal("250.00"))
                .build();

        when(client.buscaListaProdutos()).thenReturn(listaProdutosDTO);
        when(produtoService.existsPorNome(produtoDTO.getNome())).thenReturn(false);
        when(converter.toEntity(produtoDTO)).thenReturn(produtoEntity);
        when(produtoService.salvaProdutos(produtoEntity)).thenReturn(produtoEntity);
        when(produtoService.buscaTodosProdutos()).thenReturn(listaProdutosDTO);

        List<ProductsDTO> listaProductsDTTORetorno = service.buscaProdutos();

        assertEquals(listaProdutosDTO,listaProductsDTTORetorno);
        verify(client).buscaListaProdutos();
        verify(produtoService).existsPorNome(produtoDTO.getNome());
        verify(converter).toEntity(produtoDTO);
        verify(produtoService).salvaProdutos(produtoEntity);
        verify(produtoService).buscaTodosProdutos();
        verifyNoMoreInteractions(client, produtoService, converter);

    }

    @Test
    void deveBuscarProdutosENaoGravarCasoRetornoTrue(){

        List<ProductsDTO> listaProdutosDTO = new ArrayList<>();

        ProductsDTO produtoDTO = ProductsDTO.builder()
                .entityId("1234")
                .nome("Jaqueta vermelha")
                .categoria("Roupas")
                .descricao("Jaqueta vermalha com bolsos laterais e listras")
                .preco(new BigDecimal("250.00"))
                .build();

        listaProdutosDTO.add(produtoDTO);

        ProdutoEntity produtoEntity = ProdutoEntity.builder()
                .id("1234")
                .nome("Jaqueta vermelha")
                .categoria("Roupas")
                .descricao("Jaqueta vermalha")
                .preco(new BigDecimal("250.00"))
                .build();

        when(client.buscaListaProdutos()).thenReturn(listaProdutosDTO);
        when(produtoService.existsPorNome(produtoDTO.getNome())).thenReturn(true);

        ConflictException e = assertThrows(ConflictException.class, () -> service.buscaProdutos());

        assertThat(e.getMessage(), is("Produto já existente no banco de dados"));
        verify(client).buscaListaProdutos();
        verify(produtoService).existsPorNome(produtoDTO.getNome());
        verifyNoMoreInteractions(client, produtoService);
        verifyNoInteractions(converter);

    }

    @Test
    void deveGerarExceptionCasoErroAoBuscarProdutosViaClient() {

        when(client.buscaListaProdutos()).thenThrow(new RuntimeException("Erro ao buscar Lista de Produtos"));

        BusinessException e = assertThrows(BusinessException.class, () -> service.buscaProdutos());

        assertThat(e.getMessage(), is("Erro ao buscar e gravar produtos no BD"));
        verify(client).buscaListaProdutos();
        verifyNoMoreInteractions(client);
        verifyNoInteractions(converter, produtoService);

    }

}