package br.com.postech.producao.adapters.dto.requests;

import br.com.postech.producao.core.enums.CategoriaProduto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

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

    @Schema(description = "Imagem do produto em BLOB.")
    private String imagem;

    @NotBlank(message = "Preço do produto é mandatorio")
    @Schema(description = "Preço do produto.")
    private BigDecimal preco;
}