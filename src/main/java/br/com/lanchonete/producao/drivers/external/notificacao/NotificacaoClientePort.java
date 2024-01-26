package br.com.lanchonete.producao.drivers.external.notificacao;

import br.com.lanchonete.producao.core.entities.Cliente;

public interface NotificacaoClientePort {
    void notificaCliente(Cliente cliente, String mensagem);
}
