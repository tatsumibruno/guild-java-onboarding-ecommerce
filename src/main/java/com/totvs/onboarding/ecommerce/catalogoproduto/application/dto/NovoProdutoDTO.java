package com.totvs.onboarding.ecommerce.catalogoproduto.application.dto;

import com.totvs.onboarding.ecommerce.catalogoproduto.domain.CodigoProduto;
import com.totvs.onboarding.ecommerce.catalogoproduto.domain.Produto;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
public class NovoProdutoDTO {
    @NotNull(message = "{NovoProdutoDTO.codigo.NotNull}")
    private final CodigoProduto codigo;
    @NotEmpty(message = "{NovoProdutoDTO.descricao.NotEmpty}")
    private final String descricao;
    @NotNull(message = "{NovoProdutoDTO.preco.NotNull}")
    @PositiveOrZero(message = "{NovoProdutoDTO.preco.PositiveOrZero}")
    private final BigDecimal preco;

    public Produto toEntity() {
        return new Produto(codigo, descricao, preco);
    }
}
