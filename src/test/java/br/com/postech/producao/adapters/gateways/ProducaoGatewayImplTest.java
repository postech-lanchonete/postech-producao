package br.com.postech.producao.adapters.gateways;

import br.com.postech.producao.business.exceptions.BadRequestException;
import br.com.postech.producao.core.entities.Pedido;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProducaoGatewayImplTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ProducaoGatewayImpl producaoGateway;

    @Test
    void atualizarPedido_DeveRetornarRespostaCorreta() throws JsonProcessingException {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        String jsonPagamento = "{\"id\":1}";
        when(objectMapper.writeValueAsString(pedido)).thenReturn(jsonPagamento);

        producaoGateway.atualizarPedido(pedido);

        verify(objectMapper, times(1)).writeValueAsString(pedido);
        verify(kafkaTemplate, times(1)).send(anyString(), eq(jsonPagamento));
    }

    @Test
    void atualizarPedido_DeveRetornarErroAoSerializar() throws JsonProcessingException {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        when(objectMapper.writeValueAsString(pedido)).thenThrow(JsonProcessingException.class);

        assertThrows(BadRequestException.class, () -> producaoGateway.atualizarPedido(pedido));

        verify(objectMapper, times(1)).writeValueAsString(pedido);
        verify(kafkaTemplate, times(0)).send(anyString(), anyString());
    }

    @Test
    void atualizarPedido_DeveRetornarErroGenerico() throws JsonProcessingException {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        when(objectMapper.writeValueAsString(pedido)).thenThrow(IllegalArgumentException.class);

        assertThrows(BadRequestException.class, () -> producaoGateway.atualizarPedido(pedido));

        verify(objectMapper, times(1)).writeValueAsString(pedido);
        verify(kafkaTemplate, times(0)).send(anyString(), anyString());
    }


}