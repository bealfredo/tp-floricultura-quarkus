package br.unitins.topicos1.floricultura.service;

import java.io.File;
import java.util.List;

import br.unitins.topicos1.floricultura.dto.PlantaCriarRascunhoDTO;
import br.unitins.topicos1.floricultura.dto.PlantaDTO;
import br.unitins.topicos1.floricultura.dto.PlantaResponseDTO;
import br.unitins.topicos1.floricultura.dto.PlantaUpdateAddRemoveQuantidadeDTO;
import br.unitins.topicos1.floricultura.dto.PlantaUpdateStatusPlantaDTO;
import br.unitins.topicos1.floricultura.form.PlantaImageForm;
import jakarta.validation.Valid;

public interface PlantaService {

    // public PlantaResponseDTO insert(@Valid PlantaDTO dto);

    public PlantaResponseDTO criarRascunho(@Valid PlantaCriarRascunhoDTO dto);

    public PlantaResponseDTO update(@Valid PlantaDTO dto, Long id);

    public void delete(Long id);

    public List<PlantaResponseDTO> findAll();    

    public PlantaResponseDTO findById(Long id);

    public List<PlantaResponseDTO> findByNome(String nome);

    // public PlantaResponseDTO findByCodigo(String codigo);

    // public List<PlantaResponseDTO> findByTag(Long id);

    // public List<PlantaResponseDTO> findByFornecedor(Long id);

    // public List<PlantaResponseDTO> findByStatusPlanta(Integer id);

    // public List<PlantaResponseDTO> findByNivelDificuldade(Long id);

    // public List<PlantaResponseDTO> findByNivelToxidade(Long id);

    // public List<PlantaResponseDTO> findByPortePlanta(String porte);

    public PlantaResponseDTO adicionarImagem(PlantaImageForm form, Long id);

    public File downloadImagem(String nomeImagem, Long id);

    public void deleteImagem(String nomeImagem, Long id);

    public void definirImagemPrincipal(String nomeImagem, Long id);

    public void updateStatusPlanta(PlantaUpdateStatusPlantaDTO dto, Long id);

    public void updateAddRemoveQuantidade(PlantaUpdateAddRemoveQuantidadeDTO dto, Long id);


}
