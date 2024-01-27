package br.com.postech.producao.drivers.external.notificacao;

import br.com.postech.producao.core.entities.Cliente;

public interface NotificacaoClientePort {
    void notificaCliente(Cliente cliente, String mensagem);
}
