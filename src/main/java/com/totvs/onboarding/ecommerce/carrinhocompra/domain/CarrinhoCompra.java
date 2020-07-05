package com.totvs.onboarding.ecommerce.carrinhocompra.domain;

import com.google.common.base.Preconditions;
import com.totvs.onboarding.ecommerce.catalogoproduto.domain.Produto;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

@Entity
@Table(name = "carrinho_compra", uniqueConstraints = {
        @UniqueConstraint(name = "carrinho_compra_cliente_un", columnNames = "email_cliente")
})
public class CarrinhoCompra {
    @Id
    @Getter
    @GeneratedValue
    @Column(name = "carrinho_compra_id")
    private UUID id;
    @Valid
    @Getter
    @Embedded
    private Cliente cliente;
    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;
    @Enumerated(EnumType.STRING)
    private TipoFrete tipoFrete;
    @Valid
    @Embedded
    private EnderecoEntrega endereco;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "carrinho", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CarrinhoCompraItem> itens = new ArrayList<>();

    /**
     * @deprecated
     */
    @Deprecated
    public CarrinhoCompra() {
        //Não utilizar, construtor criado somente para o funcionamento do framework
    }

    private CarrinhoCompra(@Valid Cliente cliente) {
        this.cliente = cliente;
    }

    public static CarrinhoCompra of(Cliente cliente) {
        Preconditions.checkArgument(nonNull(cliente), "O Cliente não pode ser nulo");
        return new CarrinhoCompra(cliente);
    }

    public CarrinhoCompra comItens(List<CarrinhoCompraItem> itens) {
        this.itens = new ArrayList<>(itens);
        return this;
    }

    public void adicionarProduto(Produto produto, int quantidade) {
        Preconditions.checkArgument(nonNull(produto), "O Produto não pode ser nulo");
        itens.add(CarrinhoCompraItem.of(this, produto, quantidade));
    }

    public <T> List<T> mapearItens(Function<CarrinhoCompraItem, T> mapeamento) {
        return itens.stream().map(mapeamento).collect(toList());
    }

    public void removerItensComProduto(Produto produto) {
        this.itens.removeIf(item -> item.isProduto(produto));
    }
}
