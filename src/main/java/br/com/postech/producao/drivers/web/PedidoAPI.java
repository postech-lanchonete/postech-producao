package br.com.postech.producao.drivers.web;

import br.com.postech.producao.adapters.dto.PedidoResponseDTO;
import br.com.postech.producao.adapters.dto.requests.PedidoRequestDto;
import br.com.postech.producao.core.enums.StatusDoPedido;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@RequestMapping("/v1/pedidos")
@Tag(name = "Pedidos", description = "Todas as operações referentes aos pedidos")
public interface PedidoAPI {

    @Operation(
            summary = "Busca pedidos",
            description = "Busca todos os pedidos."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PedidoResponseDTO.class)))
            }),
    })
    List<PedidoResponseDTO> buscarTodos(@Parameter(description = "Status do pedido, podendo ser 'RECEBIDO', 'EM_PREPARACAO', 'PRONTO' ou 'FINALIZADO'", example = "RECEBIDO", schema = @Schema(type = "string", allowableValues = {"RECEBIDO", "EM_PREPARACAO", "PRONTO", "FINALIZADO"}))
                                        @RequestParam(required = false, name = "status") String status);


    @Operation(
            summary = "Mudar status de um pedido",
            description = "Muda o pedido, seguindo o fluxo: recebido > em preparação > pronto > finalizado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Pedido alterado." ),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado.", content = { @Content(schema = @Schema()) })
    })
    PedidoResponseDTO mudarStatus(@NotNull(message = "ID do pedido é obrigatório") Long id,
                                  @RequestParam(required = false) StatusDoPedido novoStatus);

}
