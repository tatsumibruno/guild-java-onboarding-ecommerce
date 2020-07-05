package com.totvs.onboarding.ecommerce.carrinhocompra.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Embeddable
@Getter
@ToString
@EqualsAndHashCode(of = "email")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Cliente {
    @Column(name = "nome_cliente")
    private String nome;
    @NotNull
    @NotEmpty
    @Column(name = "email_cliente")
    private String email;

    public Cliente(@NotNull @NotEmpty String email) {
        this.email = email;
    }

    public static Cliente of(String email) {
        return new Cliente(email);
    }

    public static Cliente of(String nome, String email) {
        return new Cliente(nome, email);
    }
}
