package br.com.postech.producao.business.usecases.implementation;

import br.com.postech.producao.drivers.external.PedidoGateway;
import br.com.postech.producao.business.usecases.UseCase;
import br.com.postech.producao.core.entities.Pedido;
import br.com.postech.producao.core.enums.StatusDoPedido;
import org.springframework.stereotype.Component;

@Component("pedidoCriarUseCase")
public class PedidoCriarUseCase implements UseCase<Pedido, Pedido> {

    private final PedidoGateway pedidoGateway;

    public PedidoCriarUseCase(PedidoGateway pedidoGateway) {
        this.pedidoGateway = pedidoGateway;
    }

    @Override
    public Pedido realizar(Pedido pedido) {
        pedido.setStatus(StatusDoPedido.RECEBIDO);
        return pedidoGateway.salvar(pedido);
    }

}
