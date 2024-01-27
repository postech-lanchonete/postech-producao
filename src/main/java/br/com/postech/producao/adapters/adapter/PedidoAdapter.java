package br.com.postech.producao.adapters.adapter;

import br.com.postech.producao.adapters.dto.PedidoResponseDTO;
import br.com.postech.producao.adapters.dto.requests.PedidoRequestDto;
import br.com.postech.producao.core.entities.Pedido;
import br.com.postech.producao.core.enums.StatusDoPedido;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface PedidoAdapter {
    PedidoResponseDTO toDto(Pedido pedido);

    default Pedido toEntity(StatusDoPedido statusDoPedido){
        Pedido pedido = new Pedido();
        pedido.setStatus(statusDoPedido);
        return pedido;
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    Pedido toEntity(PedidoRequestDto requestDto);

    @AfterMapping
    default void setDefaultValues(@MappingTarget Pedido pedido) {
        pedido.setDataCriacao(LocalDateTime.now());
        pedido.setStatus(StatusDoPedido.RECEBIDO);
    }
}
