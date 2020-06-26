package com.totvs.onboarding.ecommerce.catalogoproduto.domain;

import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;

import static com.google.common.base.Strings.isNullOrEmpty;

@Embeddable
@EqualsAndHashCode(of = "valor")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CodigoProduto {

    @NotEmpty
    @Column(name = "codigo")
    private String valor;

    public static CodigoProduto from(String valor) {
        Preconditions.checkArgument(!isNullOrEmpty(valor), "O código deve ser preenchido.");
        return new CodigoProduto(valor);
    }

    public String asString() {
        return valor;
    }

    @Override
    public String toString() {
        return asString();
    }
}
