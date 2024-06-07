package br.unitins.topicos1.floricultura.service;

import java.util.List;

import br.unitins.topicos1.floricultura.dto.ClienteExistingUserDTO;
import br.unitins.topicos1.floricultura.dto.ClienteFastCreateDTO;
import br.unitins.topicos1.floricultura.dto.ClienteResponseDTO;
import br.unitins.topicos1.floricultura.dto.ClienteUpdateCarrinhoDTO;
import br.unitins.topicos1.floricultura.dto.ClienteUpdateDTO;
import br.unitins.topicos1.floricultura.dto.EnderecoDTO;
import br.unitins.topicos1.floricultura.model.Endereco;
import jakarta.validation.Valid;

public interface ClienteService {

    public String insert(@Valid ClienteFastCreateDTO dto);

    public ClienteResponseDTO update(@Valid ClienteUpdateDTO dto, Long id);

    public void delete(Long id);

    public ClienteResponseDTO findById(Long id);

    public ClienteResponseDTO findByToken();

    public List<ClienteResponseDTO> findByAll(int page, int pageSize); 

    public Long count();

    public String insertExistingUser(@Valid ClienteExistingUserDTO dto);

    public void updateCarrinho(@Valid ClienteUpdateCarrinhoDTO dto);

    public String getCarrinho();

    // public List<EnderecoDTO> getListaEndereco();

}
