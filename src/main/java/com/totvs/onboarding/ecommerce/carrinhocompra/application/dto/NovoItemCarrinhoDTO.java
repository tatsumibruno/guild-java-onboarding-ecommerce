package com.totvs.onboarding.ecommerce.carrinhocompra.application.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.UUID;

@Data
public class NovoItemCarrinhoDTO {
    @NotNull(message = "{ItemCarrinhoDTO.produtoId.NotNull}")
    private UUID produtoId;
    @Positive(message = "{ItemCarrinhoDTO.quantidade.Positive}")
    private int quantidade;


}
