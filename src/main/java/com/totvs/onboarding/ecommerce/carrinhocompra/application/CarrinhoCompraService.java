package com.totvs.onboarding.ecommerce.carrinhocompra.application;

import com.totvs.onboarding.ecommerce.carrinhocompra.application.dto.CarrinhoCompraDTO;
import com.totvs.onboarding.ecommerce.carrinhocompra.application.dto.NovoItemCarrinhoDTO;
import com.totvs.onboarding.ecommerce.carrinhocompra.domain.CarrinhoCompra;
import com.totvs.onboarding.ecommerce.carrinhocompra.domain.CarrinhoCompraRepository;
import com.totvs.onboarding.ecommerce.carrinhocompra.domain.Cliente;
import com.totvs.onboarding.ecommerce.carrinhocompra.exceptions.CarrinhoCompraNaoEncontradoException;
import com.totvs.onboarding.ecommerce.catalogoproduto.domain.Produto;
import com.totvs.onboarding.ecommerce.catalogoproduto.domain.ProdutoRepository;
import com.totvs.onboarding.ecommerce.catalogoproduto.exceptions.ProdutoNaoEncontradoException;
import com.totvs.onboarding.ecommerce.commons.application.ApplicationService;
import com.totvs.tjf.core.validation.ValidatorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class CarrinhoCompraService extends ApplicationService {

    private final CarrinhoCompraRepository carrinhoCompraRepository;
    private final ProdutoRepository produtoRepository;

    public CarrinhoCompraService(ValidatorService validatorService,
                                 CarrinhoCompraRepository carrinhoCompraRepository,
                                 ProdutoRepository produtoRepository) {
        super(validatorService);
        this.carrinhoCompraRepository = carrinhoCompraRepository;
        this.produtoRepository = produtoRepository;
    }

    public CarrinhoCompraDTO adicionarItem(String emailCliente, NovoItemCarrinhoDTO novoItem) {
        validate(novoItem);
        final UUID produtoId = novoItem.getProdutoId();
        final Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(produtoId));
        final CarrinhoCompra carrinhoCompra = carrinhoCompraRepository.findByEmailCliente(emailCliente)
                .orElse(CarrinhoCompra.of(Cliente.of(emailCliente)));
        carrinhoCompra.adicionarProduto(produto, novoItem.getQuantidade());
        carrinhoCompraRepository.save(carrinhoCompra);
        return CarrinhoCompraDTO.from(carrinhoCompra);
    }

    public CarrinhoCompraDTO removerProduto(String emailCliente, UUID produtoId) {
        final Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(produtoId));
        final CarrinhoCompra carrinhoCompra = carrinhoCompraRepository.findByEmailCliente(emailCliente)
                .orElse(CarrinhoCompra.of(Cliente.of(emailCliente)));
        carrinhoCompra.removerItensComProduto(produto);
        carrinhoCompraRepository.save(carrinhoCompra);
        return CarrinhoCompraDTO.from(carrinhoCompra);
    }

    public CarrinhoCompraDTO buscarPorCliente(String emailCliente) {
        return carrinhoCompraRepository.findByEmailCliente(emailCliente)
                .map(CarrinhoCompraDTO::from)
                .orElseThrow(() -> new CarrinhoCompraNaoEncontradoException(emailCliente));
    }
}
