package br.com.postech.producao.adapters.input.subscribers;

import br.com.postech.producao.drivers.external.DeadLetterQueueGateway;
import br.com.postech.producao.drivers.external.ProducaoGateway;
import br.com.postech.producao.business.usecases.implementation.PedidoCriarUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProducaoSubscriberTest {

    @Mock
    private PedidoCriarUseCase pedidoCriarUseCase;

    @Mock
    private ProducaoGateway producaoGateway;
    @Mock
    private DeadLetterQueueGateway deadLetterQueueGateway;

    @Spy
    private ObjectMapper objectMapper;

    @InjectMocks
    private ProducaoSubscriber pagamentoSubscriber;

    @Test
    void consumeSuccess_Success() {
        String value = "{\"id\":123,\"status\":\"FINALIZADO\"}";
        pagamentoSubscriber.consumeSuccess(value);

        verify(pedidoCriarUseCase, times(1)).realizar(any());
        verify(producaoGateway, times(1)).atualizarPedido(any());
    }

    @Test
    void consumeSuccess_Failure() {
        String value = "FALHA\"id\":123,\"status\":\"FINALIZADO\"}";

        pagamentoSubscriber.consumeSuccess(value);

        verify(deadLetterQueueGateway, times(1)).enviar(anyString(), eq(value));
    }
}