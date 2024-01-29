//package br.com.postech.producao.bdd.steps;
//
//import br.com.postech.producao.adapters.dto.ClienteDto;
//import br.com.postech.producao.adapters.dto.PedidoResponseDTO;
//import br.com.postech.producao.adapters.dto.ProdutoResponseDTO;
//import br.com.postech.producao.adapters.dto.requests.PedidoRequestDto;
//import br.com.postech.producao.adapters.dto.requests.ProdutoRequestDto;
//import br.com.postech.producao.bdd.helper.RequestHelper;
//import br.com.postech.producao.core.enums.CategoriaProduto;
//import br.com.postech.producao.core.enums.StatusDoPedido;
//import io.cucumber.java.pt.Dado;
//import io.cucumber.java.pt.E;
//import io.cucumber.java.pt.Entao;
//import io.cucumber.java.pt.Quando;
//import org.junit.Assert;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;
//
//import java.math.BigDecimal;
//import java.util.Arrays;
//import java.util.Objects;
//
//public class PedidoStepDefinition2 {
//    private static final String PATCH = "/v1/pedidos";
//
//    private RequestHelper<PedidoResponseDTO> requestSingleHelper;
//    private RequestHelper<ProdutoResponseDTO[]> requestListHelper;
//
//    private ProdutoResponseDTO produtoEncontrado;
//    private ClienteDto clienteEncontrado;
//    private PedidoResponseDTO pedidoEncontrado;
//    private String statusPesquisa;
//
//    @Dado("que exista um produto com o nome {string}")
//    public void dadoQueExista(String nome) {
//        this.produtoEncontrado = Arrays.stream(Objects.requireNonNull(RequestHelper
//                        .realizar("/v1/produtos", HttpMethod.GET, null, ProdutoResponseDTO[].class)
//                        .validaStatusEqualsTo(HttpStatus.OK).getSuccessResponse().getBody()))
//                .filter(p -> p.getNome().equals(nome)).findAny()
//                .orElse(null);
//
//        Assert.assertNotNull("Produto não encontrado", produtoEncontrado);
//    }
//    @E("que exista um cliente com o cpf {string}")
//    public void queExistaUmClienteRegistradoNoSistemaComOCPF(String cpf) {
//        this.clienteEncontrado = RequestHelper
//                .realizar("/v1/clientes/" + cpf, HttpMethod.GET, null, ClienteDto.class).getSuccessResponse().getBody();
//    }
//
//    @E("que nao exista um cliente com o cpf {string}")
//    public void queNaoExistaUmClienteRegistradoNoSistemaComOCPF(String cpf) {
//        RequestHelper.realizar("/v1/clientes/" + cpf, HttpMethod.GET, null, ClienteDto.class)
//                .validaStatusEqualsTo(HttpStatus.NOT_FOUND);
//    }
//
//    @Entao("deve retornar o pedido")
//    public void deveRetornarOPedido() {
//        Assert.assertNotNull(this.requestSingleHelper.getSuccessResponse().getBody());
//    }
//
////    @E("o status da requisicao do pedido deve ser igual a {int}")
////    public void oStatusDaRequisicaoDoPedidoDeveSerIgualA(int status) {
////        if(this.requestSingleHelper.getSuccessResponse() != null) {
////            Assert.assertEquals("Status should match", HttpStatusCode.valueOf(status), this.requestSingleHelper.getSuccessResponse().getStatusCode());
////        } else {
////            Assert.assertEquals("Status should match", HttpStatusCode.valueOf(status), this.requestSingleHelper.getErrorResponse().getStatusCode());
////        }
////    }
//
//    @Dado("que se queira criar um pedido com um produto com id inexistente")
//    public void queSeQueiraCriarUmPedidoComUmProdutoComId() {}
//
//    @E("a mensagem de erro ao criar o pedido contenha {string}")
//    public void aMensagemDeErroAoCriarOPedidoContenha(String mensagem) {
//        Assert.assertTrue(this.requestSingleHelper.getErrorResponse().getResponseBodyAsString().contains(mensagem));
//    }
//
//    @Dado("que exista um pedido com o status {string}")
//    public void queExistaUmPedidoComOStatus(String statusString) {
//        StatusDoPedido status = StatusDoPedido.encontrarEnumPorString(statusString);
//        PedidoResponseDTO[] pedidos = RequestHelper
//                .realizar(PATCH, HttpMethod.GET, null, PedidoResponseDTO[].class)
//                .validaStatusEqualsTo(HttpStatus.OK).getSuccessResponse().getBody();
//
//        assert pedidos != null;
//        this.pedidoEncontrado = Arrays.stream(pedidos)
//                .filter(p -> p.getStatus().equals(status)).findAny()
//                .orElse(null);
//
//        Assert.assertNotNull("Pedido não encontrado", pedidoEncontrado);
//        Assert.assertEquals("O status deve corresponder", status, pedidoEncontrado.getStatus());
//    }
//
////    @Quando("enviar uma requisição para alterar o status deste pedido")
////    public void enviarUmaRequisicaoParaAlterarOStatusDestePedido() {
////        this.requestSingleHelper = RequestHelper
////                .realizar(PATCH + "/" + this.pedidoEncontrado.getId() + "/status", HttpMethod.PUT, null, PedidoResponseDTO.class);
////    }
//
//    @E("o status deste pedido deve ser igual a {string}")
//    public void oStatusDestePedidoDeveSerIgualA(String status) {
//        Assert.assertEquals("Status should match",
//                StatusDoPedido.encontrarEnumPorString(status),
//                Objects.requireNonNull(this.requestSingleHelper.getSuccessResponse().getBody()).getStatus());
//    }
//
//    @Dado("que se queira buscar os pedidos com o status igual a {string}")
//    public void queSeQueiraBuscarOsPedidosComOStatusIgualA(String status) {
//        this.statusPesquisa = status;
//    }
//
//    @Quando("for feita uma busca pelos pedidos")
//    public void forFeitaUmaBuscaPelosPedidos() {
//        this.requestListHelper = RequestHelper
//                .realizar(PATCH + "?status="+this.statusPesquisa, HttpMethod.GET, null, ProdutoResponseDTO[].class);
//    }
//
//    @Entao("o resultado da busca de pedidos deve conter uma lista com {int} pedidos")
//    public void oResultadoDaBuscaDePedidosDeveConterUmaListaComPedidos(int tamanhoLista) {
//        Assert.assertEquals("Tamanho da lista igual", tamanhoLista, Objects.requireNonNull(this.requestListHelper.getSuccessResponse().getBody()).length);
//    }
//
//    @E("o status da busca dos pedidos deve ser igual a {int}")
//    public void oStatusDaBuscaDosPedidosDeveSerIgualA(int status) {
//        Assert.assertEquals("Status da requisicao igual", HttpStatus.valueOf(status), Objects.requireNonNull(this.requestListHelper.getSuccessResponse().getStatusCode()));
//    }
//
//    @Entao("o resultado da busca de pedidos deve retornar um erro {int}")
//    public void oResultadoDaBuscaDePedidosDeveRetornarUmErro(int statusErro) {
//        Assert.assertEquals("Status da requisicao igual", HttpStatus.valueOf(statusErro), Objects.requireNonNull(this.requestListHelper.getErrorResponse().getStatusCode()));
//    }
//
//    @E("conter um erro da mensagem contendo {string}")
//    public void conterUmErroDaMensagemContendo(String mensagemErro) {
//        Assert.assertTrue("Mensagem de erro deve conter", Objects.requireNonNull(this.requestListHelper.getErrorResponse().getMessage()).contains(mensagemErro));
//    }
//
//    @Quando("enviar uma requisição para criar um pedido para um cliente com o cpf {string} com o produto inexistente")
//    public void enviarUmaRequisicaoParaCriarUmPedidoParaUmClienteComOCpfComOProdutoInexistente(String cpf) {
//        var produtoHamburguer = criarProdutoRequest("Hambúrguer", CategoriaProduto.LANCHE, BigDecimal.valueOf(32.5));
//        var produtoBatataFrita = criarProdutoRequest("Batata Frita", CategoriaProduto.ACOMPANHAMENTO, BigDecimal.valueOf(15.0));
//
//        PedidoRequestDto pedidoDTO = new PedidoRequestDto();
//        pedidoDTO.setCliente(ClienteDto.builder().nome("Nome").sobrenome("Sobrenome").cpf(cpf).email("nome.sobrenome@test.com").build());
//        pedidoDTO.setProdutos(Arrays.asList(produtoHamburguer, produtoBatataFrita));
//
//        this.requestSingleHelper = RequestHelper
//                .realizar(PATCH, HttpMethod.POST, pedidoDTO, PedidoResponseDTO.class);
//    }
//
//    @Quando("enviar uma requisição para criar um pedido para um cliente com o cpf {string} com o produto encontrado")
//    public void criarPedidoParaCpfInvalido(String cpf) {
//        var produtoHamburguer = criarProdutoRequest("Hambúrguer", CategoriaProduto.LANCHE, BigDecimal.valueOf(32.5));
//        var produtoBatataFrita = criarProdutoRequest("Batata Frita", CategoriaProduto.ACOMPANHAMENTO, BigDecimal.valueOf(15.0));
//
//        PedidoRequestDto pedidoDTO = new PedidoRequestDto();
//        pedidoDTO.setCliente(ClienteDto.builder().nome("Nome").sobrenome("Sobrenome").cpf(cpf).email("nome.sobrenome@test.com").build());
//        pedidoDTO.setProdutos(Arrays.asList(produtoHamburguer, produtoBatataFrita));
//
//        this.requestSingleHelper = RequestHelper
//                .realizar(PATCH, HttpMethod.POST, pedidoDTO, PedidoResponseDTO.class);
//
//    }
//    @Quando("enviar uma requisição para criar um pedido para o cliente encontrado com o produto encontrado")
//    public void enviarRequisicaoDeCriacao() {
//        var produtoHamburguer = criarProdutoRequest("Hambúrguer", CategoriaProduto.LANCHE, BigDecimal.valueOf(32.5));
//        var produtoBatataFrita = criarProdutoRequest("Batata Frita", CategoriaProduto.ACOMPANHAMENTO, BigDecimal.valueOf(15.0));
//
//        PedidoRequestDto pedidoDTO = new PedidoRequestDto();
//        pedidoDTO.setCliente(ClienteDto.builder().nome("Nome").sobrenome("Sobrenome").cpf(clienteEncontrado.getCpf()).email("nome.sobrenome@test.com").build());
//        pedidoDTO.setProdutos(Arrays.asList(produtoHamburguer, produtoBatataFrita));
//
//        this.requestSingleHelper = RequestHelper
//                .realizar(PATCH, HttpMethod.POST, pedidoDTO, PedidoResponseDTO.class);
//
//    }
//
//    private ProdutoRequestDto criarProdutoRequest(String nome, CategoriaProduto categoria, BigDecimal preco) {
//        ProdutoRequestDto produto = new ProdutoRequestDto();
//        produto.setNome(nome);
//        produto.setCategoria(categoria);
//        produto.setImagem("");
//        return produto;
//    }
//
//
//}
