package br.com.postech.producao.adapters.dto.requests;

import br.com.postech.producao.adapters.dto.ClienteDto;
import br.com.postech.producao.core.enums.StatusPagamento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unused")
public class PedidoRequestDto {
    private StatusPagamento statusPagamento;
    private ClienteDto cliente;
    private List<ProdutoRequestDto> produtos;
}
