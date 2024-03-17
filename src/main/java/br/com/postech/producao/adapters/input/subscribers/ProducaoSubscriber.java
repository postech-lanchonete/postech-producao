package br.com.postech.producao.adapters.input.subscribers;

import br.com.postech.producao.drivers.external.DeadLetterQueueGateway;
import br.com.postech.producao.drivers.external.ProducaoGateway;
import br.com.postech.producao.business.usecases.UseCase;
import br.com.postech.producao.core.entities.Pedido;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class ProducaoSubscriber {

    public static final String TOPIC_PRODUCAO_INPUT = "postech-producao-input";
    public static final String TOPIC_PRODUCAO_INPUT_DLQ = "postech-producao-input-dlq";
    private final UseCase<Pedido, Pedido> criarUseCase;
    private final ProducaoGateway producaoGateway;
    private final DeadLetterQueueGateway deadLetterQueueGateway;
    private final ObjectMapper objectMapper;

    public ProducaoSubscriber(@Qualifier("pedidoCriarUseCase") UseCase<Pedido, Pedido> criarUseCase,
                              ProducaoGateway producaoGateway, DeadLetterQueueGateway deadLetterQueueGateway, ObjectMapper objectMapper) {
        this.criarUseCase = criarUseCase;
        this.producaoGateway = producaoGateway;
        this.deadLetterQueueGateway = deadLetterQueueGateway;
        this.objectMapper = objectMapper;
    }

    @Transactional
    @KafkaListener(topics = TOPIC_PRODUCAO_INPUT, groupId = "postech-group-producao")
    public void consumeSuccess(String value) {
        try {
            Pedido pedidoRecebido = objectMapper.readValue(value, Pedido.class);
            Pedido pedidoCrido = criarUseCase.realizar(pedidoRecebido);

            producaoGateway.atualizarPedido(pedidoCrido);
        } catch (Exception e) {
            log.error("Erro ao processar a mensagem JSON: " + e.getMessage());
            this.deadLetterQueueGateway.enviar(TOPIC_PRODUCAO_INPUT_DLQ, value);
        }
    }
}
