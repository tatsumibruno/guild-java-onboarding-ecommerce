package com.totvs.onboarding.ecommerce.carrinhocompra.application;

import com.totvs.onboarding.ecommerce.carrinhocompra.application.dto.CarrinhoCompraDTO;
import com.totvs.onboarding.ecommerce.carrinhocompra.application.dto.NovoItemCarrinhoDTO;
import com.totvs.tjf.api.context.stereotype.ApiGuideline;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/carrinhoCompras")
@ApiGuideline(ApiGuideline.ApiGuidelineVersion.v1)
@Api(tags = "Carrinho de Compras")
public class CarrinhoCompraController {

    private CarrinhoCompraService carrinhoCompraService;

    public CarrinhoCompraController(CarrinhoCompraService carrinhoCompraService) {
        this.carrinhoCompraService = carrinhoCompraService;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("Adiciona um Produto ao Carrinho de Compras")
    public ResponseEntity<CarrinhoCompraDTO> adicionaItem(@RequestHeader(name = "emailCliente") String emailCliente,
                                                          @RequestBody NovoItemCarrinhoDTO novoItemCarrinho) {
        CarrinhoCompraDTO carrinho = carrinhoCompraService.adicionarItem(emailCliente, novoItemCarrinho);
        return ResponseEntity.ok(carrinho);
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("Busca o Carrinho de Compras do usuário")
    public ResponseEntity<CarrinhoCompraDTO> buscarPorCliente(@RequestHeader(name = "emailCliente") String emailCliente) {
        CarrinhoCompraDTO carrinho = carrinhoCompraService.buscarPorCliente(emailCliente);
        return ResponseEntity.ok(carrinho);
    }

    @DeleteMapping(path = "/produto/{produtoId}")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("Remove um Produto do Carrinho de Compras")
    public ResponseEntity<CarrinhoCompraDTO> adicionaItem(@RequestHeader(name = "emailCliente") String emailCliente,
                                                          @PathVariable UUID produtoId) {
        CarrinhoCompraDTO carrinho = carrinhoCompraService.removerProduto(emailCliente, produtoId);
        return ResponseEntity.ok(carrinho);
    }
}
