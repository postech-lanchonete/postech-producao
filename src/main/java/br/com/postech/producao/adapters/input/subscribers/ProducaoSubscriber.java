package br.com.postech.producao.adapters.input.subscribers;

import br.com.postech.producao.adapters.gateways.ProducaoGateway;
import br.com.postech.producao.business.exceptions.BadRequestException;
import br.com.postech.producao.business.usecases.UseCase;
import br.com.postech.producao.core.entities.Pedido;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProducaoSubscriber {

    private final UseCase<Pedido, Pedido> criarUseCase;
    private final ProducaoGateway producaoGateway;
    private final ObjectMapper objectMapper;

    public ProducaoSubscriber(@Qualifier("pedidoCriarUseCase") UseCase<Pedido, Pedido> criarUseCase,
                              ProducaoGateway producaoGateway, ObjectMapper objectMapper) {
        this.criarUseCase = criarUseCase;
        this.producaoGateway = producaoGateway;
        this.objectMapper = objectMapper;
    }


    @KafkaListener(topics = "postech-producao-input", groupId = "postech-group-producao")
    public void consumeSuccess(String value) {
        try {
            Pedido pedidoRecebido = objectMapper.readValue(value, Pedido.class);
            Pedido pedidoCrido = criarUseCase.realizar(pedidoRecebido);

            producaoGateway.atualizarPedido(pedidoCrido);
        } catch (Exception e) {
            log.error("Erro ao processar a mensagem JSON: " + e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }
}
