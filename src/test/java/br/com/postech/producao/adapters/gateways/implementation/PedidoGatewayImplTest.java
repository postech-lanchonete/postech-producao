package br.com.postech.producao.adapters.gateways.implementation;

import br.com.postech.producao.adapters.repositories.PedidoRepository;
import br.com.postech.producao.core.entities.Pedido;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PedidoGatewayImplTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private PedidoGatewayImpl pedidoGatewayImpl;

    @Test
    void testBuscarPorExample() {
        Pedido pedido = new Pedido();
        Example<Pedido> pedidoExample = Example.of(pedido);
        List<Pedido> expectedPedidos = Arrays.asList(new Pedido(), new Pedido());

        when(pedidoRepository.findAll(pedidoExample)).thenReturn(expectedPedidos);

        List<Pedido> result = pedidoGatewayImpl.buscarPor(pedidoExample);

        assertEquals(expectedPedidos, result);
        verify(pedidoRepository, times(1)).findAll(pedidoExample);
    }

    @Test
    void testSalvar() {
        Pedido pedido = new Pedido();

        when(pedidoRepository.save(pedido)).thenReturn(pedido);

        Pedido result = pedidoGatewayImpl.salvar(pedido);

        assertEquals(pedido, result);
        verify(pedidoRepository, times(1)).save(pedido);
    }

    @Test
    void testBuscarPorIdPedidoExistente() {
        Long id = 1L;
        Pedido expectedPedido = new Pedido();

        when(pedidoRepository.findById(id)).thenReturn(Optional.of(expectedPedido));

        Pedido result = pedidoGatewayImpl.buscarPorId(id);

        assertEquals(expectedPedido, result);
        verify(pedidoRepository, times(1)).findById(id);
    }
}