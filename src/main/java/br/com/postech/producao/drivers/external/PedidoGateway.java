package br.com.postech.producao.drivers.external;

import br.com.postech.producao.core.entities.Pedido;

public interface PedidoGateway extends Gateway<Pedido> {
    Pedido buscarPorId(Long id);
}