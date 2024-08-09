package com.example.Products.controllers;

import com.example.Products.api.dto.request.ProdutoRequestDTO;
import com.example.Products.api.dto.response.ProdutoResponseDTO;
import com.example.Products.business.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
@Tag(name="API Produtos")
public class FakeApiController {

    private final ProdutoService produtoService;

    @Operation(summary = "Salva novos produtos", method ="POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto salvo com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro ao salvar os produtos"),
    })
    @PostMapping("/")
    public ResponseEntity<ProdutoResponseDTO> salvaProdutos(@RequestBody ProdutoRequestDTO produtoRequestDTO){
        return ResponseEntity.ok(produtoService.salvaNovoProdutos(produtoRequestDTO));
    }

    @Operation(summary = "Atualizar Produto por ID", method ="PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto salvo com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro ao salvar os produtos"),
    })
    @PutMapping("/")
    public ResponseEntity<ProdutoResponseDTO> updateProdutos(@RequestParam ("id") String id, @RequestBody ProdutoRequestDTO produtoRequestDTO){
        return ResponseEntity.ok(produtoService.atualizarProdutoPorId(produtoRequestDTO, id));
    }

    @Operation(summary = "Deletar Produto por ID", method ="DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto deletado com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro ao deletar os produtos"),
    })
    @DeleteMapping("/")
    public ResponseEntity<Void> deletaProduto(@RequestParam ("id") String id){
        produtoService.deletarProdutoPorId(id);
        return ResponseEntity.accepted().build();

    }

    @Operation(summary = "Buscar Todo os Produtos", method ="GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto salvo com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro ao salvar os produtos"),
    })
    @GetMapping("/")
    public ResponseEntity<List<ProdutoResponseDTO>> buscaTodosProdutos(){
        return ResponseEntity.ok(produtoService.buscaTodosProdutosCadastrados());
    }

    @Operation(summary = "Buscar Produtos por ID", method ="GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto salvo com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro ao salvar os produtos"),
    })
    @GetMapping("/search")
    public ResponseEntity<ProdutoResponseDTO> buscaProdutosPorNome(@RequestParam ("id") String id){
        return ResponseEntity.ok(produtoService.buscarProdutoPorId(id));
    }
}