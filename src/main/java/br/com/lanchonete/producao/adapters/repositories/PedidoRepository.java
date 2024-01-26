package br.com.lanchonete.producao.adapters.repositories;

import br.com.lanchonete.producao.core.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}