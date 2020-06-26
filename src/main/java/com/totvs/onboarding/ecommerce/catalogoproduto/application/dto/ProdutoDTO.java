package com.totvs.onboarding.ecommerce.catalogoproduto.application.dto;

import com.totvs.onboarding.ecommerce.catalogoproduto.domain.CodigoProduto;
import com.totvs.onboarding.ecommerce.catalogoproduto.domain.Produto;
import com.totvs.onboarding.ecommerce.catalogoproduto.domain.StatusProduto;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Getter
public class ProdutoDTO {
    private final UUID id;
    private final CodigoProduto codigo;
    private final String descricao;
    private final BigDecimal preco;
    private final StatusProduto status;

    public static ProdutoDTO from(Produto produto) {
        return new ProdutoDTO(produto.getId(),
                produto.getCodigo(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getStatus());
    }
}
