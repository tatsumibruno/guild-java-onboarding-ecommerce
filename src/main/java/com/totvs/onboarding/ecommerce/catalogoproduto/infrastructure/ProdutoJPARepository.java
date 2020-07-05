package com.totvs.onboarding.ecommerce.catalogoproduto.infrastructure;

import com.google.common.base.Strings;
import com.totvs.onboarding.ecommerce.catalogoproduto.domain.Produto;
import com.totvs.onboarding.ecommerce.catalogoproduto.domain.ProdutoRepository;
import com.totvs.tjf.api.context.v1.request.ApiFieldRequest;
import com.totvs.tjf.api.context.v1.request.ApiPageRequest;
import com.totvs.tjf.api.context.v1.request.ApiSortRequest;
import com.totvs.tjf.api.context.v1.response.ApiCollectionResponse;
import com.totvs.tjf.api.jpa.ApiRequestConverter;
import com.totvs.tjf.api.jpa.repository.ApiJpaRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

interface ProdutoJPARepository extends ProdutoRepository, CrudRepository<Produto, UUID>, ApiJpaRepository<Produto> {

    @Override
    @Caching(evict = @CacheEvict(value = "Produto.findById", key = "#produto.id"))
    Produto save(Produto produto);

    @Override
    @Cacheable("Produto.findById")
    Optional<Produto> findById(UUID id);

    @Override
    default ApiCollectionResponse<Produto> findByDescricao(ApiPageRequest pageRequest, ApiSortRequest sortRequest, String descricao) {
        if (Strings.isNullOrEmpty(descricao)) {
            return findAllProjected(new ApiFieldRequest(), pageRequest, sortRequest);
        }
        Pageable pageable = ApiRequestConverter.convert(pageRequest, sortRequest);
        Page<Produto> pageResult = findByDescricao(descricao, pageable);
        return ApiCollectionResponse.of(pageResult.getContent(), pageResult.hasNext());
    }

    @Query("select p from Produto p where UPPER(p.descricao) like CONCAT('%', UPPER(:descricao), '%')")
    Page<Produto> findByDescricao(String descricao, Pageable pageable);

    @Override
    @Caching(evict = @CacheEvict(value = "Produto.findById", key = "#id"))
    void deleteById(UUID id);
}
