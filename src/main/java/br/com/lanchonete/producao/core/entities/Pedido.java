package br.com.lanchonete.producao.core.entities;

import br.com.lanchonete.producao.core.enums.StatusDoPedido;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@SuppressWarnings("unused")
@Entity
@Table(name = "Pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "pedidos_produtos",
            joinColumns = @JoinColumn(name = "pedido_id"),
            inverseJoinColumns = @JoinColumn(name = "produto_id"))
    private List<Produto> produtos;

    @Enumerated(EnumType.STRING)
    private StatusDoPedido status;

    private LocalDateTime dataCriacao;

    public Pedido() {
    }

    public Pedido(Cliente cliente, List<Produto> produtos) {
        this.status = StatusDoPedido.RECEBIDO;
        this.dataCriacao = LocalDateTime.now();
        this.cliente = cliente;
        this.produtos = produtos;
    }
}