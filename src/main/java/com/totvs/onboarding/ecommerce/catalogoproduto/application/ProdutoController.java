package com.totvs.onboarding.ecommerce.catalogoproduto.application;

import com.totvs.onboarding.ecommerce.catalogoproduto.application.dto.NovoProdutoDTO;
import com.totvs.onboarding.ecommerce.catalogoproduto.application.dto.ProdutoDTO;
import com.totvs.onboarding.ecommerce.catalogoproduto.domain.StatusProduto;
import com.totvs.tjf.api.context.stereotype.ApiGuideline;
import com.totvs.tjf.api.context.v1.request.ApiFieldRequest;
import com.totvs.tjf.api.context.v1.request.ApiPageRequest;
import com.totvs.tjf.api.context.v1.request.ApiSortRequest;
import com.totvs.tjf.api.context.v1.response.ApiCollectionResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.UUID;

@Api(tags = "Catálogo de Produtos")
@RestController
@ApiGuideline(ApiGuideline.ApiGuidelineVersion.v1)
@RequestMapping(path = "/api/v1/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @ApiOperation("Cria um novo Produto")
    public ResponseEntity<ProdutoDTO> criar(@RequestBody NovoProdutoDTO novoProduto) {
        ProdutoDTO produto = produtoService.criar(novoProduto);
        return ResponseEntity.created(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/")
                .path(produto.getId().toString()).build().toUri())
                .body(produto);
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("Busca um Produto pelo ID")
    public ResponseEntity<ProdutoDTO> buscarPorId(@PathVariable UUID id) {
        ProdutoDTO produto = produtoService.buscarPorId(id);
        return ResponseEntity.ok(produto);
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("Retorna a listagem de Produtos com a opção de filtrar por parte da descrição")
    public ResponseEntity<ApiCollectionResponse<ProdutoDTO>> buscarPorFiltro(ApiFieldRequest fieldRequest,
            ApiPageRequest pageRequest,
            ApiSortRequest sortRequest,
            String descricao) {
        return ResponseEntity.ok(produtoService.buscarPorFiltro(pageRequest, sortRequest, descricao));
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("Altera um Produto existente")
    public ResponseEntity<ProdutoDTO> alterar(@PathVariable UUID id, @RequestBody NovoProdutoDTO produto) {
        ProdutoDTO produtoAlterado = produtoService.alterar(id, produto);
        return ResponseEntity.ok(produtoAlterado);
    }

    @PutMapping(path = "/{id}/status/{novoStatus}")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("Altera o status de um Produto")
    public ResponseEntity<ProdutoDTO> alterarStatus(@PathVariable UUID id, @PathVariable StatusProduto novoStatus) {
        ProdutoDTO produtoAlterado = produtoService.alterarStatus(id, novoStatus);
        return ResponseEntity.ok(produtoAlterado);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @ApiOperation("Remove um Produto existente")
    public void excluir(@PathVariable UUID id) {
        produtoService.deletar(id);
    }
}
