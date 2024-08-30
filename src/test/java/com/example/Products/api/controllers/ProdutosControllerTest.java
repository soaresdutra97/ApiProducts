package com.example.Products.api.controllers;

import com.example.Products.api.dto.request.ProdutoRequestDTO;
import com.example.Products.api.dto.response.ProdutoResponseDTO;
import com.example.Products.business.service.ProdutoService;
import com.example.Products.controllers.ProdutosController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.List;
import java.math.BigDecimal;
import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ProdutosControllerTest {

    @InjectMocks
    ProdutosController controller;

    @Mock
    ProdutoService service;

    private MockMvc mockMvc;

    private String json;

    private ProdutoRequestDTO produtoRequestDTO;

    private ProdutoResponseDTO produtoResponseDTO;

    private String url;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;


    @BeforeEach
    void setup() throws JsonProcessingException {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).alwaysDo(print()).build();
        url = "/produtos";
        produtoRequestDTO = ProdutoRequestDTO.builder()
                .nome("Jaqueta Vermelha")
                .categoria("Roupas")
                .descricao("Jaqueta vermelha com bolsos")
                .preco(new BigDecimal(500))
                .build();
        json = objectMapper.writeValueAsString(produtoRequestDTO);

    }

    @Test
    void deveSalvarProdutosComSucesso() throws Exception {

        when(service.salvaNovoProdutos(produtoRequestDTO)).thenReturn(produtoResponseDTO);
        mockMvc.perform(post(url + "/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service).salvaNovoProdutos(produtoRequestDTO);
        verifyNoMoreInteractions(service);

    }

    @Test
    void naoDeveEnviarRequestCasoResponseDTOSejaNull() throws Exception {

        mockMvc.perform(post(url + "/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(service);

    }


    @Test
    void deveAtualizarProdutosComSucesso() throws Exception {

        String id = "123456";

        when(service.atualizarProdutoPorId(produtoRequestDTO, id)).thenReturn(produtoResponseDTO);
        mockMvc.perform(put(url + "/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .param("id", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service).atualizarProdutoPorId(produtoRequestDTO, id);
        verifyNoMoreInteractions(service);

    }

    @Test
    void naoDeveEnviarRequestCasoIDSejaNull() throws Exception {


        mockMvc.perform(put(url + "/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(service);

    }

    @Test
    void deveDeletarProdutosComSucesso() throws Exception {

        String id = "123456";

        doNothing().when(service).deletarProdutoPorId(id);
        when(kafkaTemplate.send("atualizaCarrinhoeWishList",id)).thenReturn(null);
        mockMvc.perform(delete(url + "/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());


        verify(service).deletarProdutoPorId(id);
        verify(kafkaTemplate).send("atualizaCarrinhoeWishList", id);
        verifyNoMoreInteractions(service, kafkaTemplate);

    }

    @Test
    void naoDeveEnviarRequestDeleteCasoIDSejaNull() throws Exception {


        mockMvc.perform(delete(url + "/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(service);

    }

    @Test
    void deveBuscarTodosOsProdutosComSucesso() throws Exception {

        List<ProdutoResponseDTO> listaProdutoResponseDTO = new ArrayList<>();
        ProdutoResponseDTO produtoResponseDTO = ProdutoResponseDTO.builder().id(1245L).nome("Jaqueta Vermelha").categoria("Roupas").descricao("Jaqueta Vermelha com bolsos laterais e Listras").preco(new BigDecimal(250.00)).build();
        listaProdutoResponseDTO.add(produtoResponseDTO);
        when(service.buscaTodosProdutosCadastrados()).thenReturn(listaProdutoResponseDTO);
        mockMvc.perform(get(url + "/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        verify(service).buscaTodosProdutosCadastrados();
        verifyNoMoreInteractions(service);

    }

    @Test
    void deveBuscarProdutoPorIDComSucesso() throws Exception {

        String id = "123456";

        when(service.buscarProdutoPorId(id)).thenReturn(produtoResponseDTO);
        mockMvc.perform(get(url + "/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", id)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verifyNoMoreInteractions(service);

    }

    @Test
    void naoDeveEnviarRequestBuscaPorIDCasoIDSejaNull() throws Exception {

        mockMvc.perform(get(url + "/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(service);

    }

}