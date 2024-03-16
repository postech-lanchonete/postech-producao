package br.com.postech.producao.adapters.gateways.implementation;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.kafka.core.KafkaTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class DeadLetterQueueGatewayImplTest {

    @Test
    void enviar_DeveEnviarMensagemParaOTopicoCorreto() {
        KafkaTemplate<String, String> kafkaTemplateMock = mock(KafkaTemplate.class);
        DeadLetterQueueGatewayImpl gateway = new DeadLetterQueueGatewayImpl(kafkaTemplateMock);

        String topico = "meu_topico";
        String mensagem = "Minha mensagem";

        gateway.enviar(topico, mensagem);

        ArgumentCaptor<String> topicoCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> mensagemCaptor = ArgumentCaptor.forClass(String.class);
        verify(kafkaTemplateMock).send(topicoCaptor.capture(), mensagemCaptor.capture());

        assertEquals(topico, topicoCaptor.getValue());
        assertEquals(mensagem, mensagemCaptor.getValue());
    }
}
