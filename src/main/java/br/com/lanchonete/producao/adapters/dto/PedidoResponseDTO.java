package br.com.lanchonete.producao.adapters.dto;

import br.com.lanchonete.producao.core.enums.StatusDoPedido;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@SuppressWarnings("unused")
public class PedidoResponseDTO {
    private Long id;
    private List<ProdutoResponseDTO> produtos;
    private ClienteDto cliente;
    private StatusDoPedido status;
    private LocalDateTime dataCriacao;
}
