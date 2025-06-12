package br.unitins.topicos1.floricultura.service.academico.aluno;

import java.util.List;

import br.unitins.topicos1.floricultura.dto.academico.aluno.AlunoFastCreateDTO;
import br.unitins.topicos1.floricultura.dto.academico.aluno.AlunoResponseDTO;
import br.unitins.topicos1.floricultura.dto.academico.aluno.AlunoUpdateDTO;
import br.unitins.topicos1.floricultura.dto.academico.aluno.RematriculaDTO;
import jakarta.validation.Valid;

public interface AlunoService {

    public String insert(@Valid AlunoFastCreateDTO dto);

    public AlunoResponseDTO update(@Valid AlunoUpdateDTO dto, Long id);

    public AlunoResponseDTO selfUpdate(@Valid AlunoUpdateDTO dto);

    public void delete(Long id);

    public AlunoResponseDTO findById(Long id);

    public AlunoResponseDTO findByToken();

    public List<AlunoResponseDTO> findByAll(int page, int pageSize); 

    public Long count();

    public AlunoResponseDTO rematricula(@Valid RematriculaDTO dto, Long id);

    // public String insertExistingUser(@Valid AlunoExistingUserDTO dto);

    // public void updateCarrinho(@Valid AlunoUpdateCarrinhoDTO dto);

    // public String getCarrinho();

    // public List<EnderecoDTO> getListaEndereco();

}
