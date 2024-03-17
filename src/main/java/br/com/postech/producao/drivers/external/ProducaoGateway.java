package br.com.postech.producao.drivers.external;


import br.com.postech.producao.core.entities.Pedido;

public interface ProducaoGateway {

    void atualizarPedido(Pedido pedido);

}
