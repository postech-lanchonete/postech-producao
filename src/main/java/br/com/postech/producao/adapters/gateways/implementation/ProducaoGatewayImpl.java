package br.com.postech.producao.adapters.gateways.implementation;

import br.com.postech.producao.adapters.gateways.ProducaoGateway;
import br.com.postech.producao.business.exceptions.BadRequestException;
import br.com.postech.producao.core.entities.Pedido;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProducaoGatewayImpl implements ProducaoGateway {

    private static final String TOPIC_PRODUCAO_OUT = "postech-producao-output";
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public ProducaoGatewayImpl(KafkaTemplate<String, String> kafkaTemplate,
                               ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void atualizarPedido(Pedido pedido) {
        try {
            String jsonPagamento = objectMapper.writeValueAsString(pedido);
            kafkaTemplate.send(TOPIC_PRODUCAO_OUT, jsonPagamento);
        } catch (JsonProcessingException e) {
            log.error("Erro ao serializar o objeto Pedido para JSON", e);
            throw new BadRequestException("Erro ao serializar o objeto Pedido para JSON");
        } catch (Exception e) {
            log.error("Erro ao enviar o Pedido para o Kafka", e);
            throw new BadRequestException("Erro ao enviar o Pedido para producao ");
        }
    }
}
