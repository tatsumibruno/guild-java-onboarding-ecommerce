package com.totvs.onboarding.ecommerce.carrinhocompra.infrastructure;

import com.querydsl.jpa.impl.JPAQuery;
import com.totvs.onboarding.ecommerce.carrinhocompra.domain.CarrinhoCompra;
import com.totvs.onboarding.ecommerce.carrinhocompra.domain.CarrinhoCompraRepository;
import com.totvs.onboarding.ecommerce.carrinhocompra.domain.QCarrinhoCompra;
import com.totvs.onboarding.ecommerce.carrinhocompra.domain.QCarrinhoCompraItem;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
class CarrinhoCompraRepositoryImpl implements CarrinhoCompraRepository {

    private CarrinhoCompraJPARepository jpaRepository;
    private EntityManager entityManager;

    public CarrinhoCompraRepositoryImpl(CarrinhoCompraJPARepository jpaRepository, EntityManager entityManager) {
        this.jpaRepository = jpaRepository;
        this.entityManager = entityManager;
    }

    @Override
    public Optional<CarrinhoCompra> findByEmailCliente(String emailCliente) {
        QCarrinhoCompra carrinho = QCarrinhoCompra.carrinhoCompra;
        QCarrinhoCompraItem item = QCarrinhoCompraItem.carrinhoCompraItem;
        CarrinhoCompra result = new JPAQuery<CarrinhoCompra>(entityManager)
                .from(carrinho)
                .leftJoin(carrinho.itens, item)
                .fetchJoin()
                .innerJoin(item.produto)
                .fetchJoin()
                .where(carrinho.cliente.email.eq(emailCliente))
                .fetchOne();
        return Optional.ofNullable(result);
    }

    @Override
    public CarrinhoCompra save(CarrinhoCompra carrinhoCompra) {
        return jpaRepository.save(carrinhoCompra);
    }
}
