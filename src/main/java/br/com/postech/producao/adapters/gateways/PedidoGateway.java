package br.com.postech.producao.adapters.gateways;

import br.com.postech.producao.core.entities.Pedido;

public interface PedidoGateway extends Gateway<Pedido> {
    Pedido buscarPorId(Long id);
}
