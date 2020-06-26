package com.totvs.onboarding.ecommerce.catalogoproduto.exceptions;

import com.totvs.tjf.api.context.stereotype.ApiError;

@ApiError("ProdutoJaPublicado")
public class ProdutoJaPublicadoException extends RuntimeException {
}
