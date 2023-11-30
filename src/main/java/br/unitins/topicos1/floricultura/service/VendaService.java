package br.unitins.topicos1.floricultura.service;

import java.util.List;

import br.unitins.topicos1.floricultura.dto.VendaDTO;
import br.unitins.topicos1.floricultura.dto.VendaResponseDTO;
import br.unitins.topicos1.floricultura.dto.VendaUpdateStatusDTO;
import jakarta.validation.Valid;

public interface VendaService {

    public VendaResponseDTO insert(@Valid VendaDTO dto, String login);

    // public ProdutoResponseDTO update(ProdutoDTO dto, Long id);

    // // public void delete(Long id);

    public List<VendaResponseDTO> findAll();    

    public VendaResponseDTO findById(Long id);

    public List<VendaResponseDTO> findByUsuario(Long id) throws Exception;

    public VendaResponseDTO updateStatusVenda(VendaUpdateStatusDTO dto, Long id);

    // public ProdutoResponseDTO findByCodigo(String codigo);

    // public List<ProdutoResponseDTO> findByTipoProduto(Long id);

    // public List<ProdutoResponseDTO> findByFornecedor(Long id);

    // public List<ProdutoResponseDTO> findByStatusProduto(Integer id);

}
