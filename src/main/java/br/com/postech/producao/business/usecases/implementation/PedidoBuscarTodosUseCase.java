package br.com.postech.producao.business.usecases.implementation;

import br.com.postech.producao.drivers.external.PedidoGateway;
import br.com.postech.producao.business.usecases.UseCase;
import br.com.postech.producao.core.entities.Pedido;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("pedidoBuscarTodosUseCase")
public class PedidoBuscarTodosUseCase implements UseCase<Pedido, List<Pedido>> {

    private final PedidoGateway pedidoGateway;

    public PedidoBuscarTodosUseCase(PedidoGateway pedidoGateway) {
        this.pedidoGateway = pedidoGateway;
    }

    @Override
    public List<Pedido> realizar(Pedido entrada) {
        return pedidoGateway.buscarPor(Example.of(entrada));
    }

}
