package br.com.postech.producao.adapters.dto.requests;

import br.com.postech.producao.core.enums.StatusDoPedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@SuppressWarnings("unused")
@AllArgsConstructor
@NoArgsConstructor
public class MudarStatusRequestDto {
    private Long id;
    private StatusDoPedido statusDoPedido;
}
