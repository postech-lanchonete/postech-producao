package br.com.postech.producao.adapters.gateways;

import br.com.postech.producao.core.entities.Produto;

import java.util.Optional;

public interface ProdutoGateway extends Gateway<Produto> {
    Optional<Produto> buscarPorId(Long id);

}
