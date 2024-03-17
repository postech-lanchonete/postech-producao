package br.com.postech.producao.drivers.external;

public interface DeadLetterQueueGateway {
    void enviar(String pagamentoJson, String topic);
}
