package com.example.Products.infrastructure.message.consumer;

import com.example.Products.business.service.ProdutoService;
import com.example.Products.infrastructure.exceptions.BusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FakeApiConsumerTest {

    @InjectMocks
    FakeApiConsumer consumer;

    @Mock
    ProdutoService service;

    @Test
    void deveReceberMensagemProdutoDtoComSucesso(){

        ProductsDTO produtoDTO = ProductsDTO.builder()
                .descricao("Jaqueta vermalha com bolsos laterais e listras")
                .preco(new BigDecimal("250.00"))
                .build();

        doNothing().when(service).salvaProdutosConsumer(produtoDTO);

        consumer.consumerProducerProdutos(produtoDTO);
        verifyNoMoreInteractions(service);
    }

    @Test
    void deveGerarExceptionCasoErroNoConsumer(){

        ProductsDTO produtoDTO = ProductsDTO.builder()
                .descricao("Jaqueta vermalha com bolsos laterais e listras")
                .preco(new BigDecimal("250.00"))
                .build();

        doThrow(new RuntimeException("Erro ao consumir mensagem")).when(service).salvaProdutosConsumer(produtoDTO);

        BusinessException e = assertThrows(BusinessException.class, () -> consumer.consumerProducerProdutos(produtoDTO));
        assertThat(e.getMessage(), is("Erro ao consumir mensagens do kafka"));
        verify(service).salvaProdutosConsumer(produtoDTO);
        verifyNoMoreInteractions(service);
    }

}
