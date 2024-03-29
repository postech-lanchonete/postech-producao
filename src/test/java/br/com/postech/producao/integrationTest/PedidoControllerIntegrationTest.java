package br.com.postech.producao.integrationTest;

import br.com.postech.producao.adapters.repositories.PedidoRepository;
import br.com.postech.producao.core.entities.Pedido;
import br.com.postech.producao.core.entities.Produto;
import br.com.postech.producao.core.enums.CategoriaProduto;
import br.com.postech.producao.core.enums.StatusDoPedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
@AutoConfigureTestDatabase
@Testcontainers
class PedidoControllerIntegrationTest {

    @Container
    static KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));


    @DynamicPropertySource
    static void kafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
    }

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private PedidoRepository pedidoRepository;

    @BeforeEach
    void setUp() {
        // Limpar dados de teste antes de cada execução de teste
        pedidoRepository.deleteAll();
    }

    @Test
    void buscarTodos_DeveRetornarListaDePedidos_QuandoExistiremPedidosNoBanco() throws Exception {
        Produto produtoHamburguer = criarProduto("Hambúrguer", CategoriaProduto.LANCHE, BigDecimal.valueOf(32.5));
        Produto produtoBatataFrita = criarProduto("Batata Frita", CategoriaProduto.ACOMPANHAMENTO, BigDecimal.valueOf(15.0));
        criarPedido(List.of(produtoHamburguer, produtoBatataFrita), StatusDoPedido.RECEBIDO);

        mockMvc.perform(get("/v1/pedidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].cliente.id", is(1)))
                .andExpect(jsonPath("$[0].produtos[0].nome", is(produtoHamburguer.getNome())))
                .andExpect(jsonPath("$[0].produtos[0].preco", is(produtoHamburguer.getPreco().doubleValue())));
    }

    @Test
    void buscarTodos_DeveRetornarListaVazia_QuandoNaoExistiremPedidosNoBanco() throws Exception {
        mockMvc.perform(get("/v1/pedidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void buscarPorStatus_DeveRetornarListaDePedidosComStatusRecebido_QuandoExistiremPedidosComEsseStatus() throws Exception {
        Produto produtoHamburguer = criarProduto("Hambúrguer", CategoriaProduto.LANCHE, BigDecimal.valueOf(32.5));
        Produto produtoBatataFrita = criarProduto("Batata Frita", CategoriaProduto.ACOMPANHAMENTO, BigDecimal.valueOf(15.0));
        criarPedido(List.of(produtoHamburguer, produtoBatataFrita), StatusDoPedido.RECEBIDO);

        mockMvc.perform(get("/v1/pedidos?status=RECEBIDO"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void buscarPorStatus_DeveRetornarListaDePedidosComStatusRecebido_QuandoNenhumPedidoComStatusRecebidoNoBanco() throws Exception {

        mockMvc.perform(get("/v1/pedidos?status=RECEBIDO"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void buscarPorStatus_DeveRetornarBadRequest_QuandoStatusInvalido() throws Exception {
        mockMvc.perform(get("/v1/pedidos?status=XXX"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void mudarStatus_DeveAtualizarStatusParaEmPreparacao_QuandoPedidoExistirEStatusForRecebido() throws Exception {
        Produto produtoHamburguer = criarProduto("Hambúrguer", CategoriaProduto.LANCHE, BigDecimal.valueOf(32.5));
        Pedido pedido = criarPedido(new ArrayList<>(List.of(produtoHamburguer)), StatusDoPedido.RECEBIDO);
        Mockito.when(kafkaTemplate.send(Mockito.any(), Mockito.any())).thenReturn(null);

        mockMvc.perform(patch("/v1/pedidos/{id}/status", pedido.getId()))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.status", is("EM_PREPARACAO")));
    }

    @Test
    void mudarStatus_DeveAtualizarStatusParaPronto_QuandoPedidoExistirEStatusForEmPreparacao() throws Exception {
        Produto produtoHamburguer = criarProduto("Hambúrguer", CategoriaProduto.LANCHE, BigDecimal.valueOf(32.5));
        Pedido pedido = criarPedido(new ArrayList<>(List.of(produtoHamburguer)), StatusDoPedido.EM_PREPARACAO);
        Long id = pedido.getId();
        mockMvc.perform(patch(String.format("/v1/pedidos/%d/status", id)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.status", is("PRONTO")));
    }

    @Test
    @Transactional
    void mudarStatus_DeveAtualizarStatusParaFinalizado_QuandoPedidoExistirEStatusForPronto() throws Exception {
        Produto produtoHamburguer = criarProduto("Hambúrguer", CategoriaProduto.LANCHE, BigDecimal.valueOf(32.5));
        Pedido pedido = criarPedido(new ArrayList<>(List.of(produtoHamburguer)), StatusDoPedido.PRONTO);

        mockMvc.perform(patch("/v1/pedidos/{id}/status", pedido.getId()))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.status", is("FINALIZADO")));
    }

    @Test
    void mudarStatus_DeveManterStatusFinalizado_QuandoPedidoExistirEStatusForFinalizado() throws Exception {
        Produto produtoHamburguer = criarProduto("Hambúrguer", CategoriaProduto.LANCHE, BigDecimal.valueOf(32.5));
        Pedido pedido = criarPedido(new ArrayList<>(List.of(produtoHamburguer)), StatusDoPedido.FINALIZADO);

        mockMvc.perform(patch("/v1/pedidos/{id}/status", pedido.getId()))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.status", is("FINALIZADO")));
    }


    private Produto criarProduto(String nome, CategoriaProduto categoria, BigDecimal preco) {
        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setCategoria(categoria);
        produto.setPreco(preco);
        produto.setDescricao("Lorem ipsum dolor sit amet.");
        produto.setImagem("");
        return produto;
    }

    private Pedido criarPedido(List<Produto> pedidos, StatusDoPedido status) {
        Pedido pedido = new Pedido();
        pedido.setStatus(status);
        pedido.setIdCliente(1L);
        pedido.setProdutos(pedidos);
        return pedidoRepository.save(pedido);
    }
}
