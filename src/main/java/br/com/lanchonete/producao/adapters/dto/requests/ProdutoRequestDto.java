package br.com.lanchonete.producao.adapters.dto.requests;

import br.com.lanchonete.producao.core.enums.CategoriaProduto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("unused")
public class ProdutoRequestDto {

    @NotBlank(message = "Nome do produto é mandatorio")
    @Schema(description = "Nome do produto.")
    private String nome;

    @NotBlank(message = "Categoria do produto é mandatoria")
    @Schema(description = "Categoria do produto.", enumAsRef = true)
    private CategoriaProduto categoria;

    @NotBlank(message = "Imagem do produto é mandatoria")
    @Schema(description = "Imagem do produto em BLOB.")
    private String imagem;
}