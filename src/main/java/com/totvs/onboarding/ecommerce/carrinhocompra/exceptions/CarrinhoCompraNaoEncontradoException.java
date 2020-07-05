package com.totvs.onboarding.ecommerce.carrinhocompra.exceptions;

import com.totvs.tjf.api.context.stereotype.ApiError;
import com.totvs.tjf.api.context.stereotype.ApiErrorParameter;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@ApiError("CarrinhoCompraNaoEncontrado")
@AllArgsConstructor
public class CarrinhoCompraNaoEncontradoException extends RuntimeException {
    @ApiErrorParameter
    private final String emailCliente;
}
