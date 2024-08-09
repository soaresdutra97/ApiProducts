package com.example.Products.business.service;

import com.example.Products.api.dto.request.ProdutoRequestDTO;
import com.example.Products.api.dto.response.ProdutoResponseDTO;
import com.example.Products.api.dto.converter.ProdutoMapper;
import com.example.Products.api.dto.converter.ProdutoUpdateMapper;
import com.example.Products.business.entities.ProdutoEntity;
import com.example.Products.infrastructure.exceptions.BusinessException;
import com.example.Products.infrastructure.repositories.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository repository;
    private final ProdutoMapper produtoMapper;
    private final ProdutoUpdateMapper produtoUpdateMapper;

    public ProdutoEntity salvaProdutos(ProdutoEntity entity){
        try {
            return repository.save(entity);
        } catch (Exception e){
            throw new BusinessException("Erro ao salvar produtos");
        }
    }

    public ProdutoResponseDTO salvaNovoProdutos(ProdutoRequestDTO produtoRequestDTO){
        notNull(produtoRequestDTO, "Os dados do usuário são obrigatórios");
        ProdutoEntity produtoEntity = salvaProdutos(produtoMapper.converteDTOParaEntity(produtoRequestDTO));
        return produtoMapper.converteEntityParaDto(produtoEntity);
    }

    public ProdutoResponseDTO atualizarProdutoPorId(ProdutoRequestDTO produtoRequestDTO, String id){
        try {
            notNull(produtoRequestDTO, "Os dados do usuário são obrigatórios");
            ProdutoEntity produtoEntity = repository.findById(id).orElseThrow(()-> new RuntimeException("Não foi posssivel"));
            produtoUpdateMapper.updateProductFromDTO(produtoRequestDTO, produtoEntity);
            return produtoMapper.converteEntityParaDto(salvaProdutos(produtoEntity));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ProdutoResponseDTO buscarProdutoPorId(String id){
        return produtoMapper.converteEntityParaDto(repository.findById(id).orElseThrow(()-> new RuntimeException("Não encontrado")));
    }

    public void deletarProdutoPorId(String id){
        repository.deleteById(id);
    }

    public List<ProdutoResponseDTO> buscaTodosProdutosCadastrados(){
        return produtoMapper.converteEntityParaDtoList(repository.findAll());
    }

    public Boolean existsPorNome(String nome){
        try {
            return repository.existsByNome(nome);
        } catch (Exception e){
            throw new BusinessException("Erro ao buscar produto por nome");
        }
    }
}