package br.com.postech.producao.adapters.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@SuppressWarnings("unused")
public class ClienteDto {
    private String nome;
    private String sobrenome;
    private String email;
    private String cpf;
}
