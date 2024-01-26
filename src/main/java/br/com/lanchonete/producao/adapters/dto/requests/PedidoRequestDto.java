package br.com.lanchonete.producao.adapters.dto.requests;

import br.com.lanchonete.producao.adapters.dto.ClienteDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoRequestDto {
    private ClienteDto cliente;
    private List<ProdutoRequestDto> produtos;
}
