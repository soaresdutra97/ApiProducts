package com.example.FakeApiPrducts.apiv1.dto;

import com.example.FakeApiPrducts.business.service.FakeApiService;
import com.example.FakeApiPrducts.business.service.ProdutoService;
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
@Tag(name="fake-api-2")
public class FakeApiController {

    private final FakeApiService service;
    private final ProdutoService produtoService;

    @Operation(summary = "Busca produtos da API e Salvar", method ="POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @PostMapping("/api")
    public ResponseEntity<List<ProductsDTO>> salvaProdutosApi(){
        return ResponseEntity.ok(service.buscaProdutos());
    }

    @Operation(summary = "Salva novos produtos", method ="POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto salvo com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro ao salvar os produtos"),
    })
    @PostMapping("/")
    public ResponseEntity<ProductsDTO> salvaProdutos(@RequestBody ProductsDTO dto){
        return ResponseEntity.ok(produtoService.salvaProdutosDTO(dto));
    }

    @Operation(summary = "Fazer update de novos produtos", method ="PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto salvo com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro ao salvar os produtos"),
    })
    @PutMapping("/")
    public ResponseEntity<ProductsDTO> updateProdutos(@RequestParam ("id") String id, @RequestBody ProductsDTO dto){
        return ResponseEntity.ok(produtoService.updateProduto(id, dto));
    }

    @Operation(summary = "Deleta produtos", method ="DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto deletado com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro ao deletar os produtos"),
    })
    @DeleteMapping("/")
    public ResponseEntity<Void> deletaProduto(@RequestParam ("nome") String nome){
        produtoService.deletaProduto(nome);
        return ResponseEntity.accepted().build();
    }

    @Operation(summary = "Busca todoso os produtos", method ="GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto salvo com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro ao salvar os produtos"),
    })
    @GetMapping("/")
    public ResponseEntity<List<ProductsDTO>> buscaTodosProdutos(){
        return ResponseEntity.ok(produtoService.buscaTodosProdutos());
    }

    @Operation(summary = "Busca produto por nome", method ="GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto salvo com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro ao salvar os produtos"),
    })
    @GetMapping("/{nome}")
    public ResponseEntity<ProductsDTO> buscaProdutosPorNome(@PathVariable ("nome") String nome){
        return ResponseEntity.ok(produtoService.buscaProdutoPorNome(nome));
    }


}
