package br.com.lanchonete.producao.business.usecases.implementation;

import br.com.lanchonete.producao.adapters.gateways.PedidoGateway;
import br.com.lanchonete.producao.business.usecases.UseCase;
import br.com.lanchonete.producao.core.entities.Pedido;
import br.com.lanchonete.producao.core.enums.StatusDoPedido;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("pedidoCriarUseCase")
public class PedidoCriarUseCase implements UseCase<Pedido, Pedido> {

    private final PedidoGateway pedidoGateway;

    public PedidoCriarUseCase(PedidoGateway pedidoGateway) {
        this.pedidoGateway = pedidoGateway;
    }

    @Override
    @Transactional
    public Pedido realizar(Pedido pedido) {
        pedido.setStatus(StatusDoPedido.RECEBIDO);
        pedidoGateway.salvar(pedido);
        return pedido;
    }

}
