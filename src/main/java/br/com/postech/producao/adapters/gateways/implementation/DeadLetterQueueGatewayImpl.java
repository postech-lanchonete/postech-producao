package br.com.postech.producao.adapters.gateways.implementation;

import br.com.postech.producao.adapters.gateways.DeadLetterQueueGateway;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class DeadLetterQueueGatewayImpl implements DeadLetterQueueGateway {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public DeadLetterQueueGatewayImpl(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void enviar(String topico, String mensagem) {
        kafkaTemplate.send(topico, mensagem);
    }
}
