package br.com.lanchonete.producao.adapters.gateways;

import br.com.lanchonete.producao.core.entities.Produto;

import java.util.Optional;

public interface ProdutoGateway extends Gateway<Produto> {
    Optional<Produto> buscarPorId(Long id);

}
