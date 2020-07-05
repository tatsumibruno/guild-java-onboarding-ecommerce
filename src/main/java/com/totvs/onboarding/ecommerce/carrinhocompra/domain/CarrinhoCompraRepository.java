package com.totvs.onboarding.ecommerce.carrinhocompra.domain;

import java.util.Optional;

public interface CarrinhoCompraRepository {
    Optional<CarrinhoCompra> findByEmailCliente(String emailCliente);

    CarrinhoCompra save(CarrinhoCompra carrinhoCompra);
}
