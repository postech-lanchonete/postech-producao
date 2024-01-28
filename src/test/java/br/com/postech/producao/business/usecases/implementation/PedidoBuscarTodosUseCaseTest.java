package br.com.postech.producao.business.usecases.implementation;

import br.com.postech.producao.adapters.gateways.PedidoGateway;
import br.com.postech.producao.core.entities.Pedido;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class PedidoBuscarTodosUseCaseTest {

    @Mock
    private PedidoGateway pedidoGateway;

    @InjectMocks
    private PedidoBuscarTodosUseCase pedidoBuscarTodosUseCase;

    @Test
    void testBuscarTodosPedidos() {
        Pedido pedido = new Pedido();
        List<Pedido> pedidosMock = Arrays.asList(pedido);

        when(pedidoGateway.buscarPor(any(Example.class))).thenReturn(pedidosMock);

        List<Pedido> result = pedidoBuscarTodosUseCase.realizar(new Pedido());

        assertEquals(pedidosMock, result);
        verify(pedidoGateway, times(1)).buscarPor(any(Example.class));
    }

    @Test
    void testBuscarTodosPedidosSemResultados() {
        when(pedidoGateway.buscarPor(any(Example.class))).thenReturn(Collections.emptyList());

        List<Pedido> result = pedidoBuscarTodosUseCase.realizar(new Pedido());

        assertEquals(Collections.emptyList(), result);
        verify(pedidoGateway, times(1)).buscarPor(any(Example.class));
    }
}
