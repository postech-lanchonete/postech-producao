package br.com.postech.producao.business.usecases.implementation;

import br.com.postech.producao.adapters.dto.requests.MudarStatusRequestDto;
import br.com.postech.producao.adapters.gateways.PedidoGateway;
import br.com.postech.producao.core.entities.Pedido;
import br.com.postech.producao.core.enums.StatusDoPedido;
import br.com.postech.producao.drivers.external.notificacao.NotificacaoClientePort;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class PedidoMudarStatusUseCaseTest {

    @Mock
    private PedidoGateway pedidoGateway;

    @Mock
    private NotificacaoClientePort notificacaoClientePort;

    @InjectMocks
    private PedidoMudarStatusUseCase pedidoMudarStatusUseCase;



    @Test
    void testMudarStatusParaEmPreparacao() {
        Pedido pedido = new Pedido();
        pedido.setStatus(StatusDoPedido.RECEBIDO);
        pedido.setIdCliente(1L);
        when(pedidoGateway.buscarPorId(anyLong())).thenReturn(pedido);

        MudarStatusRequestDto dto = new MudarStatusRequestDto();
        dto.setId(1L);
        dto.setStatusDoPedido(StatusDoPedido.EM_PREPARACAO);

        Pedido result = pedidoMudarStatusUseCase.realizar(dto);

        assertEquals(StatusDoPedido.EM_PREPARACAO, result.getStatus());
        verify(pedidoGateway, times(1)).salvar(any(Pedido.class));
        verify(notificacaoClientePort, never()).notificaCliente(any(), anyString());
    }

    @Test
    void testMudarStatusParaPronto() {
        Pedido pedido = new Pedido();
        pedido.setStatus(StatusDoPedido.EM_PREPARACAO);
        pedido.setIdCliente(1L);

        when(pedidoGateway.buscarPorId(anyLong())).thenReturn(pedido);

        MudarStatusRequestDto dto = new MudarStatusRequestDto();
        dto.setId(1L);

        Pedido result = pedidoMudarStatusUseCase.realizar(dto);

        assertEquals(StatusDoPedido.PRONTO, result.getStatus());
        verify(pedidoGateway, times(1)).salvar(any(Pedido.class));
        verify(notificacaoClientePort, times(1)).notificaCliente(any(), anyString());
    }

    @Test
    void testMudarStatusParaFinalizado() {
        Pedido pedido = new Pedido();
        pedido.setStatus(StatusDoPedido.FINALIZADO);
        pedido.setIdCliente(1L);

        when(pedidoGateway.buscarPorId(anyLong())).thenReturn(pedido);

        MudarStatusRequestDto dto = new MudarStatusRequestDto();
        dto.setId(1L);

        Pedido result = pedidoMudarStatusUseCase.realizar(dto);

        assertEquals(StatusDoPedido.FINALIZADO, result.getStatus());
        verify(pedidoGateway, times(1)).salvar(any(Pedido.class));
        verify(notificacaoClientePort, never()).notificaCliente(any(), anyString());
    }

    @Test
    void testMudarStatusParaFinalizadoSemNotificacao() {
        Pedido pedido = new Pedido();
        pedido.setStatus(StatusDoPedido.FINALIZADO);
        pedido.setIdCliente(1L);

        when(pedidoGateway.buscarPorId(anyLong())).thenReturn(pedido);

        MudarStatusRequestDto dto = new MudarStatusRequestDto();
        dto.setId(1L);

        Pedido result = pedidoMudarStatusUseCase.realizar(dto);

        assertEquals(StatusDoPedido.FINALIZADO, result.getStatus());
        verify(pedidoGateway, times(1)).salvar(any(Pedido.class));
        verify(notificacaoClientePort, never()).notificaCliente(any(), anyString());
    }
}
