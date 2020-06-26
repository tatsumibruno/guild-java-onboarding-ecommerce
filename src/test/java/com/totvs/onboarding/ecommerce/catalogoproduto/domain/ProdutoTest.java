package com.totvs.onboarding.ecommerce.catalogoproduto.domain;

import com.totvs.onboarding.ecommerce.catalogoproduto.exceptions.ProdutoJaPublicadoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class ProdutoTest {

    @Test
    @DisplayName("Um produto criado deve ter o status pendente por padrão")
    public void statusPadraoAposCriacao() {
        Produto produto = new Produto(CodigoProduto.from("1"), "Produto 1", BigDecimal.TEN);
        Assertions.assertEquals(StatusProduto.PUBLICACAO_PENDENTE, produto.getStatus());
    }

    @Test
    @DisplayName("Validar a alteração do status do produto")
    public void alterarStatus() {
        Produto produto = new Produto(CodigoProduto.from("1"), "Produto 1", BigDecimal.TEN);
        produto.alterarStatus(StatusProduto.PUBLICADO);
        Assertions.assertEquals(StatusProduto.PUBLICADO, produto.getStatus());
    }

    @Test
    @DisplayName("Após ter o status publicado, um produto não pode ter seu status alterado")
    public void statusBloqueadoAposPublicar() {
        Produto produto = new Produto(CodigoProduto.from("1"), "Produto 1", BigDecimal.TEN);
        produto.alterarStatus(StatusProduto.PUBLICADO);
        Assertions.assertThrows(ProdutoJaPublicadoException.class, () ->
                produto.alterarStatus(StatusProduto.PUBLICACAO_PENDENTE));
    }
}
