package com.totvs.onboarding.ecommerce.carrinhocompra.application.dto;

import com.totvs.onboarding.ecommerce.carrinhocompra.domain.CarrinhoCompra;
import com.totvs.onboarding.ecommerce.carrinhocompra.domain.Cliente;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CarrinhoCompraDTO {
    private final UUID id;
    private final Cliente cliente;
    private final List<CarrinhoCompraItemDTO> itens;

    public static CarrinhoCompraDTO from(CarrinhoCompra carrinhoCompra) {
        return new CarrinhoCompraDTO(carrinhoCompra.getId(),
                carrinhoCompra.getCliente(),
                carrinhoCompra.mapearItens(CarrinhoCompraItemDTO::from));
    }
}
