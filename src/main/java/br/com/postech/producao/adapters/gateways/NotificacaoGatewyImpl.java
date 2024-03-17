package br.com.postech.producao.adapters.gateways;

import br.com.postech.producao.drivers.external.NotificacaoGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotificacaoGatewyImpl implements NotificacaoGateway {
    public void notificaCliente(Long cliente, String mensagem) {
        log.info("............. Conectando a plataforma de envio de mensagem .............");
        log.info("Esta é apenas uma simulação de envio de mensagem para o cliente {}",
                cliente);
    }

}
