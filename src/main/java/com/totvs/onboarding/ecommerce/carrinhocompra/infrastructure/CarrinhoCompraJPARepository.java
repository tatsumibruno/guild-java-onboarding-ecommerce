package com.totvs.onboarding.ecommerce.carrinhocompra.infrastructure;

import com.totvs.onboarding.ecommerce.carrinhocompra.domain.CarrinhoCompra;
import com.totvs.tjf.api.jpa.repository.ApiJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

interface CarrinhoCompraJPARepository extends CrudRepository<CarrinhoCompra, UUID>, ApiJpaRepository<CarrinhoCompra> {

    @Query("select c from CarrinhoCompra c join fetch c.itens where c.cliente.email = :emailCliente")
    Optional<CarrinhoCompra> findByEmailCliente(String emailCliente);
}
