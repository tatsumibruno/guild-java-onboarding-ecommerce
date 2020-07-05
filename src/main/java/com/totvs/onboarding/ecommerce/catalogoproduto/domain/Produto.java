package com.totvs.onboarding.ecommerce.catalogoproduto.domain;

import com.totvs.onboarding.ecommerce.catalogoproduto.exceptions.ProdutoJaPublicadoException;
import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

import static com.totvs.onboarding.ecommerce.catalogoproduto.domain.StatusProduto.PUBLICACAO_PENDENTE;
import static com.totvs.onboarding.ecommerce.catalogoproduto.domain.StatusProduto.PUBLICADO;

@Entity
@Getter
@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class Produto {
    @Id
    @Setter
    @GeneratedValue
    @Column(name = "produto_id")
    private UUID id;
    @Valid
    @Embedded
    private CodigoProduto codigo;
    @NotEmpty
    private String descricao;
    @NotNull
    private BigDecimal preco;
    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusProduto status = PUBLICACAO_PENDENTE;

    public Produto(CodigoProduto codigo, String descricao, BigDecimal preco) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.preco = preco;
    }

    public void alterarStatus(StatusProduto novoStatus) {
        if (status == PUBLICADO) {
            throw new ProdutoJaPublicadoException();
        }
        status = novoStatus;
    }
}
