package com.totvs.onboarding.ecommerce.catalogoproduto.application;

import com.totvs.onboarding.ecommerce.catalogoproduto.application.dto.NovoProdutoDTO;
import com.totvs.onboarding.ecommerce.catalogoproduto.application.dto.ProdutoDTO;
import com.totvs.onboarding.ecommerce.catalogoproduto.domain.CodigoProduto;
import com.totvs.onboarding.ecommerce.catalogoproduto.domain.Produto;
import com.totvs.onboarding.ecommerce.catalogoproduto.domain.ProdutoRepository;
import com.totvs.onboarding.ecommerce.catalogoproduto.exceptions.ProdutoNaoEncontradoException;
import com.totvs.tjf.api.context.v1.request.ApiFieldRequest;
import com.totvs.tjf.api.context.v1.request.ApiPageRequest;
import com.totvs.tjf.api.context.v1.request.ApiSortRequest;
import com.totvs.tjf.api.context.v1.response.ApiCollectionResponse;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.UUID;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Testes integrados do Catálogo de Produtos")
public class ProdutoControllerTest {

    private static final UUID idNaoExistente = UUID.fromString("4c7bd9ce-b5b6-11ea-b3de-0242ac130004");

    @Autowired
    private ProdutoController controller;
    @Autowired
    private ProdutoRepository repository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterEach
    @BeforeEach
    public void cleanup() {
        jdbcTemplate.update("DELETE FROM PRODUTO");
    }

    @Test
    @DisplayName("Um produto deve existir no banco de dados após ser criado.")
    public void criacao() {
        NovoProdutoDTO novoProduto = new NovoProdutoDTO(CodigoProduto.from("123"),
                "Produto teste",
                BigDecimal.TEN);

        ResponseEntity<ProdutoDTO> responseNovoProduto = controller.criar(novoProduto);
        ProdutoDTO produtoCriado = responseNovoProduto.getBody();

        Assertions.assertNotNull(produtoCriado);
        Assertions.assertNotNull(produtoCriado.getId());
        Assertions.assertEquals("123", produtoCriado.getCodigo().asString());
        Assertions.assertEquals("Produto teste", produtoCriado.getDescricao());
        Assertions.assertEquals(BigDecimal.TEN, produtoCriado.getPreco());

        Produto produtoDatabase = repository.findById(produtoCriado.getId()).orElseThrow();
        Assertions.assertNotNull(produtoDatabase);
        Assertions.assertEquals("123", produtoDatabase.getCodigo().asString());
        Assertions.assertEquals("Produto teste", produtoDatabase.getDescricao());
        Assertions.assertEquals(BigDecimal.TEN, produtoDatabase.getPreco());
    }

    @Test
    @DisplayName("Deve validar a entrada de dados na criação do produto")
    public void validacaoDadosCriacao() {
        Assertions.assertThrows(ConstraintViolationException.class, () ->
                controller.criar(new NovoProdutoDTO(null, "Produto Teste", BigDecimal.ONE)));
        Assertions.assertThrows(ConstraintViolationException.class, () ->
                controller.criar(new NovoProdutoDTO(CodigoProduto.from("123"), "", BigDecimal.ONE)));
        Assertions.assertThrows(ConstraintViolationException.class, () ->
                controller.criar(new NovoProdutoDTO(CodigoProduto.from("123"), null, BigDecimal.ONE)));
        Assertions.assertThrows(ConstraintViolationException.class, () ->
                controller.criar(new NovoProdutoDTO(CodigoProduto.from("123"), "Produto Teste", null)));
        Assertions.assertThrows(ConstraintViolationException.class, () ->
                controller.criar(new NovoProdutoDTO(CodigoProduto.from("123"), "Produto Teste", BigDecimal.valueOf(-1))));
    }

    @Test
    @DisplayName("Após criar um produto, deve ser possível buscá-lo pelo ID")
    public void buscaPorId() {
        Produto novoProduto = new Produto(CodigoProduto.from("123"), "Produto teste", BigDecimal.TEN);
        repository.save(novoProduto);
        ResponseEntity<ProdutoDTO> response = controller.buscarPorId(novoProduto.getId());
        ProdutoDTO produto = response.getBody();
        Assertions.assertNotNull(produto);
        Assertions.assertEquals(novoProduto.getId(), produto.getId());
        Assertions.assertEquals(novoProduto.getCodigo(), produto.getCodigo());
        Assertions.assertEquals(novoProduto.getDescricao(), produto.getDescricao());
        Assertions.assertEquals(novoProduto.getPreco(), produto.getPreco());
    }

    @Test
    @DisplayName("Ao buscar um produto com um ID não existente, deverá lançar a exceção ProdutoNaoEncontradoException")
    public void buscaPorIdNaoExistente() {
        Assertions.assertThrows(ProdutoNaoEncontradoException.class, () -> controller.buscarPorId(idNaoExistente));
    }

    @Test
    @DisplayName("Buscar os produtos utilizando um filtro com parte da descrição")
    public void buscarProdutosComFiltro() {
        Produto produto1 = new Produto(CodigoProduto.from("123"), "Produto teste 1", BigDecimal.TEN);
        Produto produto2 = new Produto(CodigoProduto.from("456"), "Produto TESTE 2", BigDecimal.TEN);
        repository.save(produto1);
        repository.save(produto2);
        ResponseEntity<ApiCollectionResponse<ProdutoDTO>> responseEntity = controller.buscarPorFiltro(new ApiFieldRequest(), new ApiPageRequest(), new ApiSortRequest(), "est");
        ApiCollectionResponse<ProdutoDTO> collectionResponse = responseEntity.getBody();
        Collection<ProdutoDTO> resultadoConsulta = collectionResponse.getItems();
        Assertions.assertEquals(2, resultadoConsulta.size());
    }

    @Test
    @DisplayName("Buscar os produtos sem utilizar o filtro")
    public void buscarProdutosSemFiltro() {
        Produto produto = new Produto(CodigoProduto.from("123"), "Produto teste 1", BigDecimal.TEN);
        repository.save(produto);
        ResponseEntity<ApiCollectionResponse<ProdutoDTO>> responseEntity = controller.buscarPorFiltro(new ApiFieldRequest(), new ApiPageRequest(), new ApiSortRequest(), null);
        ApiCollectionResponse<ProdutoDTO> collectionResponse = responseEntity.getBody();
        Collection<ProdutoDTO> resultadoConsulta = collectionResponse.getItems();
        Assertions.assertEquals(1, resultadoConsulta.size());
    }

    @Test
    @DisplayName("Após alterar um produto, os valores devem ter sido modificados no banco de dados")
    public void alteracao() {
        Produto novoProduto = new Produto(CodigoProduto.from("123"), "Produto teste", BigDecimal.TEN);
        repository.save(novoProduto);

        NovoProdutoDTO alteracao = new NovoProdutoDTO(CodigoProduto.from("456"),
                "Produto teste alterado",
                BigDecimal.ONE);

        controller.alterar(novoProduto.getId(), alteracao);

        Produto produtoDatabase = repository.findById(novoProduto.getId()).orElseThrow();
        Assertions.assertNotNull(produtoDatabase);
        Assertions.assertEquals("456", produtoDatabase.getCodigo().asString());
        Assertions.assertEquals("Produto teste alterado", produtoDatabase.getDescricao());
        Assertions.assertEquals(BigDecimal.ONE, produtoDatabase.getPreco());
    }

    @Test
    @DisplayName("Ao tentar alterar um produto que não existe, deve lançar a exceção ProdutoNaoEncontradoException")
    public void alteracaoProdutoNaoExistente() {
        Assertions.assertThrows(ProdutoNaoEncontradoException.class, () -> {
            NovoProdutoDTO alteracao = new NovoProdutoDTO(CodigoProduto.from("456"),
                    "Produto teste alterado",
                    BigDecimal.ONE);
            controller.alterar(idNaoExistente, alteracao);
        });
    }

    @Test
    @DisplayName("Após remover um produto, ele não deve mais existir no banco de dados")
    public void exclusao() {
        Produto novoProduto = new Produto(CodigoProduto.from("123"), "Produto teste", BigDecimal.TEN);
        repository.save(novoProduto);
        controller.excluir(novoProduto.getId());
        Assertions.assertFalse(repository.existsById(novoProduto.getId()));
    }

    @Test
    @DisplayName("Ao tentar remover um produto que não existe, deve lançar a exceção ProdutoNaoEncontradoException")
    public void exclusaoProdutoNaoExistente() {
        Assertions.assertThrows(ProdutoNaoEncontradoException.class, () -> controller.excluir(idNaoExistente));
    }
}
