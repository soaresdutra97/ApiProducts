package com.example.FakeApiPrducts.business.service;

import com.example.FakeApiPrducts.apiv1.dto.ProductsDTO;
import com.example.FakeApiPrducts.business.converter.ProdutoConverter;
import com.example.FakeApiPrducts.infrastructure.client.FakeApiClient;
import com.example.FakeApiPrducts.infrastructure.configs.error.NotificacaoErro;
import com.example.FakeApiPrducts.infrastructure.exceptions.BusinessException;
import com.example.FakeApiPrducts.infrastructure.exceptions.ConflictException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FakeApiService {

    private final FakeApiClient cliente;
    private final ProdutoService produtoService;
    private final ProdutoConverter converter;

    @NotificacaoErro
    public List<ProductsDTO> buscaProdutos(){

        try {

            List<ProductsDTO> dto = cliente.buscaListaProdutos();
            dto.forEach(produto -> {
                Boolean retorno = produtoService.existsPorNome(produto.getNome());
                if (retorno.equals(false)){
                    produtoService.salvaProdutos(converter.toEntity(produto));
                } else {
                    throw new ConflictException("Produto já existente no banco de dados");
                }
            });
            return produtoService.buscaTodosProdutos();
        } catch (ConflictException e){
            throw new ConflictException(e.getMessage());
        } catch (Exception e) {
            throw new BusinessException("Erro ao buscar e gravar produtos no BD");
        }
    }
}