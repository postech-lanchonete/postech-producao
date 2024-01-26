package br.com.lanchonete.producao.adapters.gateways.implementation;

import br.com.lanchonete.producao.adapters.gateways.PedidoGateway;
import br.com.lanchonete.producao.adapters.repositories.PedidoRepository;
import br.com.lanchonete.producao.business.exceptions.NotFoundException;
import br.com.lanchonete.producao.core.entities.Pedido;
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
