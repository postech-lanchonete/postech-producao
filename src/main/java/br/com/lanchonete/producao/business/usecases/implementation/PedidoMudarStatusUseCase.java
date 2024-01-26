package br.com.lanchonete.producao.business.usecases.implementation;

import br.com.lanchonete.producao.adapters.gateways.PedidoGateway;
import br.com.lanchonete.producao.business.usecases.UseCase;
import br.com.lanchonete.producao.core.entities.Pedido;
import br.com.lanchonete.producao.core.enums.StatusDoPedido;
import br.com.lanchonete.producao.drivers.external.notificacao.NotificacaoClientePort;
import org.springframework.stereotype.Component;

@Component("pedidoMudarStatusUseCase")
public class PedidoMudarStatusUseCase implements UseCase<Long, Pedido> {

    private final PedidoGateway pedidoGateway;
    private final NotificacaoClientePort notificacaoClientePort;

    public PedidoMudarStatusUseCase(PedidoGateway pedidoGateway, NotificacaoClientePort notificacaoClientePort) {
        this.pedidoGateway = pedidoGateway;
        this.notificacaoClientePort = notificacaoClientePort;
    }

    @Override
    public Pedido realizar(Long id) {
        Pedido pedido = pedidoGateway.buscarPorId(id);
        this.mudarStatus(pedido);
        pedidoGateway.salvar(pedido);
        if (pedido.getStatus() == StatusDoPedido.PRONTO) {
            notificacaoClientePort.notificaCliente(pedido.getCliente(), "Seu pedido está pronto. Venha buscar!");
        }
        return pedido;
    }

    private void mudarStatus(Pedido pedido) {
        switch (pedido.getStatus()) {
            case RECEBIDO -> pedido.setStatus(StatusDoPedido.EM_PREPARACAO);
            case EM_PREPARACAO -> pedido.setStatus(StatusDoPedido.PRONTO);
            case PRONTO -> pedido.setStatus(StatusDoPedido.FINALIZADO);
            default -> {
                // Manter o status em FINALIZADO
            }
        }
    }

}
