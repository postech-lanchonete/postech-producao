package br.com.postech.producao.drivers.external.notificacao;

public interface NotificacaoClientePort {
    void notificaCliente(Long idCliente, String mensagem);
}
