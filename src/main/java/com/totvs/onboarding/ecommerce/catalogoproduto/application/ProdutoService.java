package com.totvs.onboarding.ecommerce.catalogoproduto.application;

import com.totvs.onboarding.ecommerce.catalogoproduto.application.dto.NovoProdutoDTO;
import com.totvs.onboarding.ecommerce.catalogoproduto.application.dto.ProdutoDTO;
import com.totvs.onboarding.ecommerce.catalogoproduto.domain.Produto;
import com.totvs.onboarding.ecommerce.catalogoproduto.domain.ProdutoRepository;
import com.totvs.onboarding.ecommerce.catalogoproduto.domain.StatusProduto;
import com.totvs.onboarding.ecommerce.catalogoproduto.exceptions.ProdutoNaoEncontradoException;
import com.totvs.onboarding.ecommerce.commons.application.ApplicationService;
import com.totvs.tjf.api.context.v1.request.ApiPageRequest;
import com.totvs.tjf.api.context.v1.request.ApiSortRequest;
import com.totvs.tjf.api.context.v1.response.ApiCollectionResponse;
import com.totvs.tjf.core.validation.ValidatorService;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProdutoService extends ApplicationService {

    private final ProdutoRepository repository;

    public ProdutoService(ValidatorService validatorService, ProdutoRepository repository) {
        super(validatorService);
        this.repository = repository;
    }

    public ProdutoDTO criar(NovoProdutoDTO novoProduto) {
        validate(novoProduto);
        Produto produto = repository.save(novoProduto.toEntity());
        return ProdutoDTO.from(produto);
    }

    public ProdutoDTO buscarPorId(UUID id) {
        return repository.findById(id)
                .map(ProdutoDTO::from)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id));
    }

    public ApiCollectionResponse<ProdutoDTO> buscarPorFiltro(ApiPageRequest pageRequest, ApiSortRequest sortRequest, String descricao) {
        ApiCollectionResponse<Produto> produtos = repository.findByDescricao(pageRequest, sortRequest, descricao);
        return ApiCollectionResponse.of(produtos.getItems()
                        .stream()
                        .map(ProdutoDTO::from)
                        .collect(Collectors.toList()),
                produtos.hasNext());
    }

    public ProdutoDTO alterar(UUID id, NovoProdutoDTO alteracao) {
        validate(alteracao);
        if (!repository.existsById(id)) {
            throw new ProdutoNaoEncontradoException(id);
        }
        Produto produto = alteracao.toEntity();
        produto.setId(id);
        return ProdutoDTO.from(repository.save(produto));
    }

    public ProdutoDTO alterarStatus(UUID id, StatusProduto novoStatus) {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id));
        produto.alterarStatus(novoStatus);
        repository.save(produto);
        return ProdutoDTO.from(produto);
    }

    public void deletar(UUID id) {
        if (!repository.existsById(id)) {
            throw new ProdutoNaoEncontradoException(id);
        }
        repository.deleteById(id);
    }
}
