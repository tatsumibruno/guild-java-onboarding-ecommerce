package com.totvs.onboarding.ecommerce.catalogoproduto.exceptions;

import com.totvs.tjf.api.context.stereotype.ApiErrorParameter;
import com.totvs.tjf.api.context.stereotype.error.ApiNotFound;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@ApiNotFound("ProdutoNaoEncontrado")
public class ProdutoNaoEncontradoException extends RuntimeException {
    @Getter
    @ApiErrorParameter
    private final UUID id;
}
