package br.com.postech.producao.adapters.gateways;

import br.com.postech.producao.drivers.external.PedidoGateway;
import br.com.postech.producao.adapters.repositories.PedidoRepository;
import br.com.postech.producao.business.exceptions.NotFoundException;
import br.com.postech.producao.core.entities.Pedido;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoGatewayImpl implements PedidoGateway {
    private final PedidoRepository pedidoRepository;

    public PedidoGatewayImpl(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public List<Pedido> buscarPor(Example<Pedido> pedidoExample) {
        return pedidoRepository.findAll(pedidoExample);
    }

    @Override
    public Pedido salvar(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    @Override
    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Pedido não encontrado com o id %d", id)));
    }

}
