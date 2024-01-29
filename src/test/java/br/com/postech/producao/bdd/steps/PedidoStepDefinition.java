package br.com.postech.producao.bdd.steps;

import br.com.postech.producao.adapters.dto.ClienteDto;
import br.com.postech.producao.adapters.dto.PedidoResponseDTO;
import br.com.postech.producao.adapters.dto.ProdutoResponseDTO;
import br.com.postech.producao.adapters.dto.requests.PedidoRequestDto;
import br.com.postech.producao.adapters.dto.requests.ProdutoRequestDto;
import br.com.postech.producao.bdd.helper.RequestHelper;
import br.com.postech.producao.core.enums.CategoriaProduto;
import br.com.postech.producao.core.enums.StatusDoPedido;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.junit.Assert;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class PedidoStepDefinition {
    private static final String PATCH = "/v1/pedidos";

    private RequestHelper<PedidoResponseDTO> requestSingleHelper;
    private RequestHelper<ProdutoResponseDTO[]> requestListHelper;

    private Long idPedidoEdicao;

    private PedidoRequestDto request;
    private String statusPesquisa;

    @Dado("que se queira criar um pedido")
    public void queSeQueiraCriarUmPedidoComProdutos() {
        this.request = new PedidoRequestDto();
        this.request.setProdutos(new ArrayList<>());
    }


    @E("um produto tenha o nome de {string}, valor de R$ {int} e categoria {string}")
    public void umProdutoTenhaONomeDeValorDeR$ECategoria(String nomeProduto, double valor, String categoria) {
        var produto = new ProdutoRequestDto();
        produto.setNome(nomeProduto);
        produto.setPreco(BigDecimal.valueOf(valor));
        produto.setCategoria(CategoriaProduto.valueOf(categoria));
        produto.setImagem("Image");
        this.request.getProdutos().add(produto);
    }

    @E("o cliente tenha o cpf igual a {string}")
    public void oClienteTenhaOCpfIgualA(String cpf) {
        var cliente = ClienteDto.builder().cpf(cpf).build();
        this.request.setCliente(cliente);
    }

    @Quando("a requisicao do pedido for enviada")
    public void aRequisicaoDoPedidoForEnviada() {
        this.requestSingleHelper = RequestHelper
                .realizar(PATCH, HttpMethod.POST, this.request, PedidoResponseDTO.class);
    }

    @Entao("deve retornar o pedido criado")
    public void deveRetornarOPedidoCriado() {
        Assert.assertNotNull(this.requestSingleHelper.getSuccessResponse().getBody());
    }

    @Entao("nao deve retornar o pedido criado")
    public void naoDeveRetornarOPedidoCriado() {
        Assert.assertNotNull(this.requestSingleHelper.getErrorResponse());
    }


    @E("o status da requisicao do pedido deve ser igual a {int}")
    public void oStatusDaRequisicaoDoPedidoDeveSerIgualA(int status) {
        if(this.requestSingleHelper.getSuccessResponse() != null) {
            Assert.assertEquals("Status should match", HttpStatusCode.valueOf(status), this.requestSingleHelper.getSuccessResponse().getStatusCode());
        } else {
            Assert.assertEquals("Status should match", HttpStatusCode.valueOf(status), this.requestSingleHelper.getErrorResponse().getStatusCode());
        }
    }

    @Quando("enviar uma requisição para alterar o status deste pedido")
    public void enviarUmaRequisicaoParaAlterarOStatusDestePedido() {
        this.requestSingleHelper = RequestHelper
                .realizar(PATCH + "/" + this.idPedidoEdicao + "/status", HttpMethod.PUT, null, PedidoResponseDTO.class);
    }

    @Dado("que se queira mudar o status do pedido {long}")
    public void queSeQueiraMudarOStatusDoPedido(long idPedidoEdicao) {
        this.idPedidoEdicao = idPedidoEdicao;
    }

    @E("o status deste pedido deve ser igual a {string}")
    public void oStatusDestePedidoDeveSerIgualA(String status) {
        Assert.assertEquals("Status should match",
                StatusDoPedido.encontrarEnumPorString(status),
                Objects.requireNonNull(this.requestSingleHelper.getSuccessResponse().getBody()).getStatus());
    }

    @Dado("que se queira buscar os pedidos com o status igual a {string}")
    public void queSeQueiraBuscarOsPedidosComOStatusIgualA(String status) {
        this.statusPesquisa = status;
    }

    @Quando("for feita uma busca pelos pedidos")
    public void forFeitaUmaBuscaPelosPedidos() {
        this.requestListHelper = RequestHelper
                .realizar(PATCH + "?status="+this.statusPesquisa, HttpMethod.GET, null, ProdutoResponseDTO[].class);
    }
    @Entao("o resultado da busca de pedidos deve conter uma lista com {int} pedidos")
    public void oResultadoDaBuscaDePedidosDeveConterUmaListaComPedidos(int tamanhoLista) {
        Assert.assertEquals("Tamanho da lista igual", tamanhoLista, Objects.requireNonNull(this.requestListHelper.getSuccessResponse().getBody()).length);
    }
    @E("o status da busca dos pedidos deve ser igual a {int}")
    public void oStatusDaBuscaDosPedidosDeveSerIgualA(int status) {
        Assert.assertEquals("Status da requisicao igual", HttpStatus.valueOf(status), Objects.requireNonNull(this.requestListHelper.getSuccessResponse().getStatusCode()));
    }

    @Entao("o resultado da busca de pedidos deve retornar um erro {int}")
    public void oResultadoDaBuscaDePedidosDeveRetornarUmErro(int statusErro) {
        Assert.assertEquals("Status da requisicao igual", HttpStatus.valueOf(statusErro), Objects.requireNonNull(this.requestListHelper.getErrorResponse().getStatusCode()));
    }

        @E("conter um erro da mensagem contendo {string}")
    public void conterUmErroDaMensagemContendo(String mensagemErro) {
        Assert.assertTrue("Mensagem de erro deve conter", Objects.requireNonNull(this.requestListHelper.getErrorResponse().getMessage()).contains(mensagemErro));
    }
}
