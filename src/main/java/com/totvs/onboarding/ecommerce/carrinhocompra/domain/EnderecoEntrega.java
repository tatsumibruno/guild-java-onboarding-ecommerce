package com.totvs.onboarding.ecommerce.carrinhocompra.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EnderecoEntrega {
    @Column(name = "endereco_entrega_cidade")
    private String cidade;
    @Column(name = "endereco_entrega_rua")
    private String rua;
    @Column(name = "endereco_entrega_numero")
    private int numero;
}
