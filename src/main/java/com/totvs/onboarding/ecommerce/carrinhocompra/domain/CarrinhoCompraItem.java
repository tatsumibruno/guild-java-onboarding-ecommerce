package com.totvs.onboarding.ecommerce.carrinhocompra.domain;

import com.google.common.base.Preconditions;
import com.totvs.onboarding.ecommerce.catalogoproduto.domain.Produto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.UUID;

import static java.util.Objects.nonNull;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CarrinhoCompraItem {
    @Id
    @Getter
    @GeneratedValue
    @Column(name = "carrinho_compra_item_id")
    private UUID id;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carrinho_compra_id", foreignKey = @ForeignKey(name = "carrinho_compra_item_carrinho_id", value = ConstraintMode.CONSTRAINT))
    private CarrinhoCompra carrinho;
    @Getter
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", foreignKey = @ForeignKey(name = "carrinho_compra_produto_id", value = ConstraintMode.CONSTRAINT))
    private Produto produto;
    @Getter
    @NotNull
    @Positive
    private int quantidade;

    private CarrinhoCompraItem(CarrinhoCompra carrinho, Produto produto, int quantidade) {
        this.carrinho = carrinho;
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public static CarrinhoCompraItem of(CarrinhoCompra carrinho, Produto produto, int quantidade) {
        Preconditions.checkArgument(nonNull(carrinho));
        Preconditions.checkArgument(nonNull(produto));
        Preconditions.checkArgument(quantidade > 0);
        return new CarrinhoCompraItem(carrinho, produto, quantidade);
    }

    public boolean isProduto(Produto produto) {
        return this.produto.equals(produto);
    }
}
