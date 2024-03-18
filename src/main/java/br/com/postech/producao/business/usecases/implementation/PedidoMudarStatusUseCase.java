package br.com.postech.producao.business.usecases.implementation;

import br.com.postech.producao.adapters.dto.requests.MudarStatusRequestDto;
import br.com.postech.producao.drivers.external.NotificacaoGateway;
import br.com.postech.producao.drivers.external.PedidoGateway;
import br.com.postech.producao.business.usecases.UseCase;
import br.com.postech.producao.core.entities.Pedido;
import br.com.postech.producao.core.enums.StatusDoPedido;
import br.com.postech.producao.drivers.external.ProducaoGateway;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("pedidoMudarStatusUseCase")
public class PedidoMudarStatusUseCase implements UseCase<MudarStatusRequestDto, Pedido> {

    private final PedidoGateway pedidoGateway;

    private final ProducaoGateway producaoGateway;

    private final NotificacaoGateway notificacaoGateway;


    public PedidoMudarStatusUseCase(PedidoGateway pedidoGateway,
                                    ProducaoGateway producaoGateway,
                                    NotificacaoGateway notificacaoGateway) {
        this.pedidoGateway = pedidoGateway;
        this.producaoGateway = producaoGateway;
        this.notificacaoGateway = notificacaoGateway;
    }

    @Override
    @Transactional
    public Pedido realizar(MudarStatusRequestDto dto) {
        Pedido pedido = pedidoGateway.buscarPorId(dto.getId());
        if (dto.getStatusDoPedido() != null) {
            pedido.setStatus(dto.getStatusDoPedido());
        } else {
            this.mudarStatus(pedido);
        }
        pedidoGateway.salvar(pedido);
        producaoGateway.atualizarPedido(pedido);
        if (pedido.getStatus() == StatusDoPedido.PRONTO) {
            notificacaoGateway.notificaCliente(pedido.getIdCliente(), "Seu pedido está pronto. Venha buscar!");
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
