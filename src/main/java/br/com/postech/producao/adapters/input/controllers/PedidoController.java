package br.com.postech.producao.adapters.input.controllers;

import br.com.postech.producao.adapters.adapter.PedidoAdapter;
import br.com.postech.producao.adapters.dto.PedidoResponseDTO;
import br.com.postech.producao.adapters.dto.requests.MudarStatusRequestDto;
import br.com.postech.producao.business.usecases.UseCase;
import br.com.postech.producao.core.entities.Pedido;
import br.com.postech.producao.core.enums.StatusDoPedido;
import br.com.postech.producao.drivers.web.PedidoAPI;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
public class PedidoController implements PedidoAPI {
    private final UseCase<Pedido, List<Pedido>> pedidoBuscarTodosUseCase;
    private final UseCase<MudarStatusRequestDto, Pedido> mudarStatusUseCase;
    private final PedidoAdapter pedidoAdapter;

    public PedidoController(@Qualifier("pedidoBuscarTodosUseCase") UseCase<Pedido, List<Pedido>> pedidoBuscarTodosUseCase,
                            @Qualifier("pedidoMudarStatusUseCase") UseCase<MudarStatusRequestDto, Pedido> mudarStatusUseCase,
                            PedidoAdapter pedidoAdapter) {
        this.pedidoBuscarTodosUseCase = pedidoBuscarTodosUseCase;
        this.mudarStatusUseCase = mudarStatusUseCase;
        this.pedidoAdapter = pedidoAdapter;
    }

    @Override
    @GetMapping
    public List<PedidoResponseDTO> buscarTodos(String status) {
        Pedido pedido = new Pedido();
        if(StringUtils.isNotEmpty(status)) {
            StatusDoPedido statusDoPedido = StatusDoPedido.encontrarEnumPorString(status);
            pedido = pedidoAdapter.toEntity(statusDoPedido);
        }

        return pedidoBuscarTodosUseCase.realizar(pedido)
                .stream()
                .map(pedidoAdapter::toDto)
                .toList();
    }


    @Override
    @PutMapping("/{id}/status")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public PedidoResponseDTO mudarStatus(@PathVariable Long id,
                                         @RequestParam(required = false) StatusDoPedido novoStatus) {
        MudarStatusRequestDto requestDto = new MudarStatusRequestDto(id, novoStatus);
        var pedido = mudarStatusUseCase.realizar(requestDto);

        return pedidoAdapter.toDto(pedido);
    }
}
