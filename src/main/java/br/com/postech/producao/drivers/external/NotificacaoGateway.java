package br.com.postech.producao.drivers.external;

public interface NotificacaoGateway {
    void notificaCliente(Long idCliente, String mensagem);
}
