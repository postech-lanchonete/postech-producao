package br.com.postech.producao.adapters.adapter;

import br.com.postech.producao.adapters.dto.ClienteDto;
import br.com.postech.producao.core.entities.Cliente;
import br.com.postech.producao.adapters.dto.CriacaoClienteDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClienteAdapter {

    ClienteDto toDto(Cliente cliente);

    @Mapping(target = "id", ignore = true)
    Cliente toEntity(CriacaoClienteDTO clienteDto);

    default Cliente toEntity(String cpf) {
        Cliente cliente = new Cliente();
        cliente.setCpf(cpf);
        return cliente;
    }
}
