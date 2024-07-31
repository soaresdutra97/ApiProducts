package com.example.FakeApiPrducts.business.service;

import com.example.FakeApiPrducts.apiv1.dto.ProductsDTO;
import com.example.FakeApiPrducts.business.converter.ProdutoConverter;
import com.example.FakeApiPrducts.infrastructure.configs.error.NotificacaoErro;
import com.example.FakeApiPrducts.infrastructure.entities.ProdutoEntity;
import com.example.FakeApiPrducts.infrastructure.exceptions.BusinessException;
import com.example.FakeApiPrducts.infrastructure.exceptions.ConflictException;
import com.example.FakeApiPrducts.infrastructure.exceptions.UnprocessableEntityException;
import com.example.FakeApiPrducts.infrastructure.message.producer.FakeApiProducer;
import com.example.FakeApiPrducts.infrastructure.repositories.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@NotificacaoErro
public class ProdutoService {

    private final ProdutoRepository repository;
    private final ProdutoConverter converter;
    private final FakeApiProducer producer;


    public ProdutoEntity salvaProdutos(ProdutoEntity entity){
        try {
            return repository.save(entity);
        } catch (Exception e){
            throw new BusinessException("Erro ao salvar produtos");
        }
    }

    public Boolean existsPorNome(String nome){
        try {
            return repository.existsByNome(nome);
        } catch (Exception e){
            throw new BusinessException("Erro ao buscar produto por nome");
        }
    }

    public List<ProductsDTO> buscaTodosProdutos(){
        try {
            return converter.toListDTO(repository.findAll());
        } catch (Exception e){
            throw new BusinessException("Erro ao buscar todos os produtos");
        }
    }

    public ProductsDTO salvaProdutosDTO(ProductsDTO dto){
        try {
            Boolean retorno = existsPorNome(dto.getNome());
            if(retorno.equals(true)){
                throw new ConflictException("Produto já existe no banco.");
            }
            ProdutoEntity entity = repository.save(converter.toEntity(dto));
            return converter.toDTO(entity);
        } catch (ConflictException e){
            throw new ConflictException(e.getMessage());
        } catch (Exception e) {
            throw new BusinessException("Erro ao salvar produtos");
        }
    }


    public void salvaProdutosConsumer(ProductsDTO dto){
        try {
            Boolean retorno = existsPorNome(dto.getNome());
            if(retorno.equals(true)){
                producer.enviaRespostaCadastroProdutos("Produto " + dto.getNome() + " já existente no banco de dados.");
                throw new ConflictException("Produto já existe no banco.");
            }
            repository.save(converter.toEntity(dto));
            producer.enviaRespostaCadastroProdutos("Produto " + dto.getNome() + " gravado com sucesso.");
        } catch (ConflictException e){
            throw new ConflictException(e.getMessage());
        } catch (Exception e) {
            producer.enviaRespostaCadastroProdutos("Erro ao gravar produto " + dto.getNome());
            throw new BusinessException("Erro ao salvar produtos");
        }
    }


    public ProductsDTO updateProduto(String id, ProductsDTO dto){
        try{
            ProdutoEntity entity = repository.findById(id).orElseThrow(() -> new UnprocessableEntityException("Produto não encontrado"));
            salvaProdutos(converter.toEntityUpdate(entity, dto, id));
            return converter.toDTO(repository.findByNome(entity.getNome()));
        } catch (UnprocessableEntityException e) {
            throw e;  // Re-throw UnprocessableEntityException
        } catch (Exception e){
            throw new BusinessException("Erro ao atualizar o produto");
        }
    }


    public void deletaProduto(String nome) {
        try {
            Boolean retorno = existsPorNome(nome);
            if(retorno.equals(false)){
                throw new UnprocessableEntityException("Não foi possível deletar o produto, pois não existe");
            }
            else {
                repository.deleteByNome(nome);
            }
        } catch (UnprocessableEntityException e){
            throw new UnprocessableEntityException(e.getMessage());
        } catch (Exception e){
            throw new BusinessException("Erro ao deletar produto por nome");
        }
    }

    public ProductsDTO buscaProdutoPorNome(String nome) {
        try {
            ProdutoEntity produto = repository.findByNome(nome);
            if (Objects.isNull(produto)) {
                throw new UnprocessableEntityException("Não foram encontrados produtos com o nome especificado");
            }
            return converter.toDTO(produto);
        } catch (UnprocessableEntityException e){
            throw new UnprocessableEntityException(e.getMessage());
        } catch (Exception e){
            throw new BusinessException("Erro ao buscar produto por nome");
        }
    }
}