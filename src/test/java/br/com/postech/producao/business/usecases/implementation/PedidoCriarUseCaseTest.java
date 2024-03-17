package br.com.postech.producao.business.usecases.implementation;

import br.com.postech.producao.drivers.external.PedidoGateway;
import br.com.postech.producao.core.entities.Pedido;
import br.com.postech.producao.core.enums.StatusDoPedido;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class PedidoCriarUseCaseTest {

    @Mock
    private PedidoGateway pedidoGateway;

    @InjectMocks
    private PedidoCriarUseCase pedidoCriarUseCase;

    @Test
    void testCriarPedido() {
        Pedido pedido = new Pedido();
        Pedido pedidoMock = new Pedido();
        pedidoMock.setStatus(StatusDoPedido.RECEBIDO);

        when(pedidoGateway.salvar(any(Pedido.class))).thenReturn(pedidoMock);

        Pedido result = pedidoCriarUseCase.realizar(pedido);

        assertEquals(StatusDoPedido.RECEBIDO, result.getStatus());
        verify(pedidoGateway, times(1)).salvar(any(Pedido.class));
    }
}
