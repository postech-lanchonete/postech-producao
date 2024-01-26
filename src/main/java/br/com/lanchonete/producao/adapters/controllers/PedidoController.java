package br.com.lanchonete.producao.adapters.controllers;

import br.com.lanchonete.producao.adapters.adapter.PedidoAdapter;
import br.com.lanchonete.producao.adapters.dto.PedidoResponseDTO;
import br.com.lanchonete.producao.business.usecases.UseCase;
import br.com.lanchonete.producao.core.enums.StatusDoPedido;
import br.com.lanchonete.producao.drivers.web.PedidoAPI;
import br.com.lanchonete.producao.adapters.dto.requests.PedidoRequestDto;
import br.com.lanchonete.producao.core.entities.Pedido;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
public class PedidoController implements PedidoAPI {
    private final UseCase<Pedido, List<Pedido>> pedidoBuscarTodosUseCase;
    private final UseCase<Pedido, Pedido> criarUseCase;
    private final UseCase<Long, Pedido> mudarStatusUseCase;
    private final PedidoAdapter pedidoAdapter;

    public PedidoController(@Qualifier("pedidoBuscarTodosUseCase") UseCase<Pedido, List<Pedido>> pedidoBuscarTodosUseCase,
                            @Qualifier("pedidoCriarUseCase") UseCase<Pedido, Pedido> criarUseCase,
                            @Qualifier("pedidoMudarStatusUseCase") UseCase<Long, Pedido> mudarStatusUseCase,
                            PedidoAdapter pedidoAdapter) {
        this.pedidoBuscarTodosUseCase = pedidoBuscarTodosUseCase;
        this.criarUseCase = criarUseCase;
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
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoResponseDTO criar(@RequestBody PedidoRequestDto requestDto) {
        Pedido pedido = pedidoAdapter.toEntity(requestDto);
        Pedido pedidoCrido = criarUseCase.realizar(pedido);
        return pedidoAdapter.toDto(pedidoCrido);
    }

    @Override
    @PutMapping("/{id}/status")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public PedidoResponseDTO mudarStatus(@PathVariable Long id) {
        Pedido pedido = mudarStatusUseCase.realizar(id);
        return pedidoAdapter.toDto(pedido);
    }
}
