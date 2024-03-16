package br.com.postech.producao.adapters.gateways;

public interface DeadLetterQueueGateway {
    void enviar(String pagamentoJson, String topic);
}
