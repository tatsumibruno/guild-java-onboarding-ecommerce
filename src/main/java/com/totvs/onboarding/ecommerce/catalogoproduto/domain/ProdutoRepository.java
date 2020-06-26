package com.totvs.onboarding.ecommerce.catalogoproduto.domain;

import com.totvs.tjf.api.context.v1.request.ApiPageRequest;
import com.totvs.tjf.api.context.v1.request.ApiSortRequest;
import com.totvs.tjf.api.context.v1.response.ApiCollectionResponse;

import java.util.Optional;
import java.util.UUID;

public interface ProdutoRepository {

    Produto save(Produto produto);

    Optional<Produto> findById(UUID id);

    ApiCollectionResponse<Produto> findByDescricao(ApiPageRequest pageRequest, ApiSortRequest sortRequest, String descricao);

    boolean existsById(UUID id);

    void deleteById(UUID id);
}
