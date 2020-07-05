package com.totvs.onboarding.ecommerce.carrinhocompra.application.dto;

import com.totvs.onboarding.ecommerce.carrinhocompra.domain.CarrinhoCompraItem;
import com.totvs.onboarding.ecommerce.catalogoproduto.application.dto.ProdutoDTO;
import lombok.Data;

@Data
public class CarrinhoCompraItemDTO {
    private final ProdutoDTO produto;
    private final int quantidade;

    public static CarrinhoCompraItemDTO from(CarrinhoCompraItem item) {
        return new CarrinhoCompraItemDTO(ProdutoDTO.from(item.getProduto()), item.getQuantidade());
    }
}
