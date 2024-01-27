package br.com.postech.producao.adapters.repositories;

import br.com.postech.producao.core.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}