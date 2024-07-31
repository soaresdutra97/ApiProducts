package com.example.FakeApiPrducts.business.service;

import com.example.FakeApiPrducts.apiv1.dto.ProductsDTO;
import com.example.FakeApiPrducts.business.converter.ProdutoConverter;
import com.example.FakeApiPrducts.infrastructure.entities.ProdutoEntity;
import com.example.FakeApiPrducts.infrastructure.exceptions.BusinessException;
import com.example.FakeApiPrducts.infrastructure.exceptions.ConflictException;
import com.example.FakeApiPrducts.infrastructure.exceptions.UnprocessableEntityException;
import com.example.FakeApiPrducts.infrastructure.message.producer.FakeApiProducer;
import com.example.FakeApiPrducts.infrastructure.repositories.ProdutoRepository;
import org.hibernate.usertype.BaseUserTypeSupport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService service;
    @Mock
    private ProdutoRepository repository;
    @Mock
    private ProdutoConverter converter;
    @Mock
    private ProdutoEntity entity;

    @Mock
    FakeApiProducer producer;

    @Test
    void deveSalvarNovosProdutosNoBanco(){

        ProdutoEntity produtoEntity = ProdutoEntity.builder()
                .id("1234")
                .nome("Jaqueta vermelha")
                .categoria("Roupas")
                .descricao("Jaqueta vermalha com bolsos laterais e listras")
                .preco(new BigDecimal("250.00"))
                .build();

        when(repository.save(produtoEntity)).thenReturn(produtoEntity);
        ProdutoEntity resultado = service.salvaProdutos(produtoEntity);

        verify(repository).save(produtoEntity);
        verifyNoMoreInteractions(repository);
        assertEquals(produtoEntity, resultado);

    }

    @Test
    void deveRetornarExcessaoCasoErroAoSalvar(){

        ProdutoEntity produtoEntity = ProdutoEntity.builder()
                .id("1234")
                .nome("Jaqueta vermelha")
                .categoria("Roupas")
                .descricao("Jaqueta vermalha com bolsos laterais e listras")
                .preco(new BigDecimal("250.00"))
                .build();

        when(repository.save(produtoEntity)).thenThrow(new RuntimeException("Erro ao salvar"));

        BusinessException e = assertThrows(BusinessException.class, () -> service.salvaProdutos(produtoEntity));

        assertThat(e.getMessage(), is("Erro ao salvar produtos"));


    }

    @Test
    void deveBuscarProdutosPorNomeComSucesso(){
        String nome = "maria";

        when(repository.existsByNome(nome)).thenReturn(true);

        Boolean resultado = service.existsPorNome(nome);

        verify(repository).existsByNome(nome);
        verifyNoMoreInteractions(repository);
        assertTrue(resultado);
    }

    @Test
    void deveRetornarExcessaoQuandoProdutoNaoExiste(){

        String nome = "maria";

        when(repository.existsByNome(nome)).thenThrow(new RuntimeException("Erro ao buscar"));
        BusinessException e = assertThrows(BusinessException.class, ()-> service.existsPorNome(nome));
        assertThat(e.getMessage(), is("Erro ao buscar produto por nome"));
    }

    @Test
    void deveBuscarTodosOsProdutosComSucesso(){

        List<ProductsDTO> produtosDTO = new ArrayList<>();
        List<ProdutoEntity> produtos = new ArrayList<>();

        ProductsDTO produtoDTO = ProductsDTO.builder()
                .entityId("1234")
                .nome("Jaqueta vermelha")
                .categoria("Roupas")
                .descricao("Jaqueta vermalha com bolsos laterais e listras")
                .preco(new BigDecimal("250.00"))
                .build();

        produtosDTO.add(produtoDTO);

        ProdutoEntity produtoEntity = ProdutoEntity.builder()
                .id("1234")
                .nome("Jaqueta vermelha")
                .categoria("Roupas")
                .descricao("Jaqueta vermalha com bolsos laterais e listras")
                .preco(new BigDecimal("250.00"))
                .build();

        produtos.add(produtoEntity);

        when(repository.findAll()).thenReturn(produtos);
        when(converter.toListDTO(produtos)).thenReturn(produtosDTO);

        List<ProductsDTO> resultado = service.buscaTodosProdutos();

        verify(repository).findAll();
        verify(converter).toListDTO(produtos);
        verifyNoMoreInteractions(repository,converter);
        assertEquals(produtosDTO, resultado);

    }

    @Test
    void deveRetornarExcessaoQuandoNaoConseguirBuscarTodosOsProdutos(){

        when(repository.findAll()).thenThrow(new RuntimeException("Erro ao buscar todos os produtos"));

        BusinessException e = assertThrows(BusinessException.class, () -> service.buscaTodosProdutos());
        assertThat(e.getMessage(), is("Erro ao buscar todos os produtos"));
    }

    @Test
    void deveSalvarProdutosDtoComSucesso(){

        ProductsDTO produtoDTO = ProductsDTO.builder()
                .entityId("1234")
                .nome("Jaqueta vermelha")
                .categoria("Roupas")
                .descricao("Jaqueta vermalha com bolsos laterais e listras")
                .preco(new BigDecimal("250.00"))
                .build();

        ProdutoEntity produtoEntity = ProdutoEntity.builder()
                .id("1234")
                .nome("Jaqueta vermelha")
                .categoria("Roupas")
                .descricao("Jaqueta vermalha com bolsos laterais e listras")
                .preco(new BigDecimal("250.00"))
                .build();

        when(service.existsPorNome(produtoDTO.getNome())).thenReturn(false);
        when(converter.toEntity(produtoDTO)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(converter.toDTO(entity)).thenReturn(produtoDTO);

        ProductsDTO resultado = service.salvaProdutosDTO(produtoDTO);

        verify(repository).existsByNome(produtoDTO.getNome());
        verify(converter).toEntity(produtoDTO);
        verify(repository).save(entity);
        verify(converter).toDTO(entity);
        verifyNoMoreInteractions(repository, converter);
        assertEquals(produtoDTO, resultado);
    }

    @Test
    void deveRetornarExceçaoAoSalvarDtoComErro(){

        ProductsDTO produtoDTO = ProductsDTO.builder()
                .entityId("1234")
                .nome("Jaqueta vermelha")
                .categoria("Roupas")
                .descricao("Jaqueta vermalha com bolsos laterais e listras")
                .preco(new BigDecimal("250.00"))
                .build();

        when(repository.existsByNome(produtoDTO.getNome())).thenReturn(false);
        when(converter.toEntity(produtoDTO)).thenThrow(new RuntimeException("Erro ao converter"));

        BusinessException e = assertThrows(BusinessException.class, () -> service.salvaProdutosDTO(produtoDTO));

        assertThat(e.getMessage(), is("Erro ao salvar produtos"));

    }

    @Test
    void deveSalvarProdutosConsumerComSucesso(){

        ProductsDTO produtoDTO = ProductsDTO.builder()
                .entityId("1234")
                .nome("Jaqueta vermelha")
                .categoria("Roupas")
                .descricao("Jaqueta vermalha com bolsos laterais e listras")
                .preco(new BigDecimal("250.00"))
                .build();

        ProdutoEntity produtoEntity = ProdutoEntity.builder()
                .id("1234")
                .nome("Jaqueta vermelha")
                .categoria("Roupas")
                .descricao("Jaqueta vermelha com bolsos laterais e listras")
                .preco(new BigDecimal("250.00"))
                .build();

        when(service.existsPorNome(produtoDTO.getNome())).thenReturn(false);
        when(converter.toEntity(produtoDTO)).thenReturn(produtoEntity);
        when(repository.save(produtoEntity)).thenReturn(produtoEntity);

        service.salvaProdutosConsumer(produtoDTO);

        verify(repository).existsByNome(produtoDTO.getNome());
        verify(converter).toEntity(produtoDTO);
        verify(repository).save(produtoEntity);
        verify(producer).enviaRespostaCadastroProdutos("Produto " + produtoDTO.getNome() + " gravado com sucesso.");
        verifyNoMoreInteractions(repository,converter,producer);

    }

    @Test
    void deveLancarExcecaoSeProdutoJaExiste(){

        ProductsDTO produtoDTO = ProductsDTO.builder()
                .entityId("1234")
                .nome("Jaqueta vermelha")
                .categoria("Roupas")
                .descricao("Jaqueta vermalha com bolsos laterais e listras")
                .preco(new BigDecimal("250.00"))
                .build();

        when(service.existsPorNome(produtoDTO.getNome())).thenReturn(true);

        ConflictException e = assertThrows(ConflictException.class,() -> service.salvaProdutosConsumer(produtoDTO));
        assertThat(e.getMessage(), is("Produto já existe no banco."));
    }

    @Test
    void deveLancarExcecaoQuandoHouverErroAoSalvarProduto(){

        ProductsDTO produtoDTO = ProductsDTO.builder()
                .entityId("1234")
                .nome("Jaqueta vermelha")
                .categoria("Roupas")
                .descricao("Jaqueta vermalha com bolsos laterais e listras")
                .preco(new BigDecimal("250.00"))
                .build();

        when(service.existsPorNome(produtoDTO.getNome())).thenReturn(false);
        when(converter.toEntity(produtoDTO)).thenThrow(new RuntimeException("Erro de conversão"));

        BusinessException e = assertThrows(BusinessException.class, () -> service.salvaProdutosConsumer(produtoDTO));

        assertThat(e.getMessage(), is("Erro ao salvar produtos"));

    }

    @Test
    void deveAtualizarProdutoComSucesso(){
        String id = "1234";

        ProductsDTO produtoDTO = ProductsDTO.builder()
                .entityId("1234")
                .nome("Jaqueta vermelha")
                .categoria("Roupas")
                .descricao("Jaqueta vermelha sem ziper")
                .preco(new BigDecimal("250.00"))
                .build();

        ProdutoEntity produtoEntity = ProdutoEntity.builder()
                .id("1234")
                .nome("Jaqueta vermelha")
                .categoria("Roupas")
                .descricao("Jaqueta vermelha sem ziper")
                .preco(new BigDecimal("250.00"))
                .build();

        ProdutoEntity produtoEntityAtualizado = ProdutoEntity.builder()
                .id("1234")
                .nome("Jaqueta vermelha")
                .categoria("Roupas")
                .descricao("Jaqueta vermelha com ziper")
                .preco(new BigDecimal("300.00"))
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(produtoEntity));
        when(converter.toEntityUpdate(produtoEntity, produtoDTO, id)).thenReturn(produtoEntityAtualizado);
        when(repository.findByNome(produtoEntity.getNome())).thenReturn(produtoEntityAtualizado);
        when(repository.save(produtoEntityAtualizado)).thenReturn(produtoEntityAtualizado);
        when(converter.toDTO(produtoEntityAtualizado)).thenReturn(produtoDTO);

        ProductsDTO resultado = service.updateProduto(id, produtoDTO);

        verify(repository).findById(id);
        verify(converter).toEntityUpdate(produtoEntity, produtoDTO, id);
        verify(repository).save(produtoEntityAtualizado);
        verify(repository).findByNome(produtoEntityAtualizado.getNome());
        verifyNoMoreInteractions(repository,converter,producer);
        assertEquals(produtoDTO,resultado);

    }

    @Test
    void deveLancarExcecaoQuandoProdutoNaoEncontrado(){

         String id = "1234";

        ProductsDTO produtoDTO = ProductsDTO.builder()
                .entityId("1234")
                .nome("Jaqueta vermelha")
                .categoria("Roupas")
                .descricao("Jaqueta vermelha sem ziper")
                .preco(new BigDecimal("250.00"))
                .build();

        when(repository.findById(id)).thenReturn(Optional.empty());

        UnprocessableEntityException e = assertThrows(UnprocessableEntityException.class,()-> service.updateProduto(id,produtoDTO));

        assertThat(e.getMessage(), is("Produto não encontrado"));
        verify(repository).findById(id);
        verifyNoMoreInteractions(repository,converter,producer);

    }

    @Test
    void deveLancarExcecaoQuandoHouverErroAoAtualizarProduto(){
        String id = "1234";

        ProductsDTO produtoDTO = ProductsDTO.builder()
                .entityId("1234")
                .nome("Jaqueta vermelha")
                .categoria("Roupas")
                .descricao("Jaqueta vermelha sem ziper")
                .preco(new BigDecimal("250.00"))
                .build();

        ProdutoEntity produtoEntity = ProdutoEntity.builder()
                .id("1234")
                .nome("Jaqueta vermelha")
                .categoria("Roupas")
                .descricao("Jaqueta vermelha sem ziper")
                .preco(new BigDecimal("250.00"))
                .build();

        ProdutoEntity produtoEntityAtualizado = ProdutoEntity.builder()
                .id("1234")
                .nome("Jaqueta vermelha")
                .categoria("Roupas")
                .descricao("Jaqueta vermelha com ziper")
                .preco(new BigDecimal("300.00"))
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(produtoEntity));
        when(converter.toEntityUpdate(produtoEntity, produtoDTO, id)).thenThrow(new RuntimeException("Erro de atualização"));

        BusinessException e = assertThrows(BusinessException.class, ()-> service.updateProduto(id, produtoDTO));
        assertThat(e.getMessage(), is("Erro ao atualizar o produto"));
        verify(repository).findById(id);
        verify(converter).toEntityUpdate(produtoEntity, produtoDTO, id);
        verifyNoMoreInteractions(repository,converter,producer);

    }

    @Test
    void deveDeletarProdutoComSucesso(){

        String nome = "Jaqueta azul";

        when(service.existsPorNome(nome)).thenReturn(true);

        service.deletaProduto(nome);

        verify(repository).existsByNome(nome);
        verify(repository).deleteByNome(nome);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void deveLancarExcecaoQuandoProdutoNaoExistir() {
        String nome = "Jaqueta azul";

        when(service.existsPorNome(nome)).thenReturn(false);

        UnprocessableEntityException e = assertThrows(UnprocessableEntityException.class,()-> service.deletaProduto(nome));
        assertThat(e.getMessage(), is("Não foi possível deletar o produto, pois não existe"));
        verify(repository).existsByNome(nome);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void deveLancarExcecaoErroAoDeletarProdutoPorNome(){

        String nome= "Jaqueta azul";

        when(service.existsPorNome(nome)).thenReturn(true);
        doThrow(new RuntimeException("Erro ao deletar")).when(repository).deleteByNome(nome);

        BusinessException e = assertThrows(BusinessException.class, ()-> service.deletaProduto(nome));


        assertThat(e.getMessage(), is("Erro ao deletar produto por nome"));
        verify(repository).existsByNome(nome);
        verify(repository).deleteByNome(nome);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void deveBuscarProdutoPorNomeComSucesso(){

        String nome = "Caneta azul";

        ProductsDTO produtoDTO = ProductsDTO.builder()
                .entityId("1234")
                .nome("Jaqueta vermelha")
                .categoria("Roupas")
                .descricao("Jaqueta vermelha sem ziper")
                .preco(new BigDecimal("250.00"))
                .build();

        ProdutoEntity produtoEntity = ProdutoEntity.builder()
                .id("1234")
                .nome("Jaqueta vermelha")
                .categoria("Roupas")
                .descricao("Jaqueta vermelha sem ziper")
                .preco(new BigDecimal("250.00"))
                .build();

        when(repository.findByNome(nome)).thenReturn(produtoEntity);
        when(converter.toDTO(produtoEntity)).thenReturn(produtoDTO);

        ProductsDTO resultado = service.buscaProdutoPorNome(nome);

        assertEquals(produtoDTO, resultado);
        verify(repository).findByNome(nome);
        verify(converter).toDTO(produtoEntity);
        verifyNoMoreInteractions(repository,converter);

    }

    @Test
    void deveRetornarExcecaoQuandoNaoForEncontradoProduto(){
        String nome = "caneta azul";

        when(repository.findByNome(nome)).thenReturn(null);

        UnprocessableEntityException e = assertThrows(UnprocessableEntityException.class,()-> service.buscaProdutoPorNome(nome));

        assertThat(e.getMessage(), is("Não foram encontrados produtos com o nome especificado"));
        verify(repository).findByNome(nome);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void deveRetornarExcecaoQuandoHouverErroAoBuscarPorNome(){

        String nome = "caneta azul";

        ProdutoEntity produtoEntity = ProdutoEntity.builder()
                .id("1234")
                .nome("Jaqueta vermelha")
                .categoria("Roupas")
                .descricao("Jaqueta vermelha sem ziper")
                .preco(new BigDecimal("250.00"))
                .build();

        when(repository.findByNome(nome)).thenReturn(produtoEntity);
        when(converter.toDTO(produtoEntity)).thenThrow(new RuntimeException("Erro ao buscar"));

        BusinessException e = assertThrows(BusinessException.class,()-> service.buscaProdutoPorNome(nome));

        assertThat(e.getMessage(), is("Erro ao buscar produto por nome"));
        verify(repository).findByNome(nome);
        verifyNoMoreInteractions(repository,converter);
    }

}