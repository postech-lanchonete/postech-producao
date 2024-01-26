package br.com.lanchonete.producao.adapters.gateways;

import br.com.lanchonete.producao.core.entities.Pedido;

public interface PedidoGateway extends Gateway<Pedido> {
    Pedido buscarPorId(Long id);
}
