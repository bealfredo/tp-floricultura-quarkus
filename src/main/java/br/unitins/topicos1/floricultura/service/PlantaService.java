package br.unitins.topicos1.floricultura.service;

import java.io.File;
import java.util.List;

import br.unitins.topicos1.floricultura.dto.NivelDificuldadeResponseDTO;
import br.unitins.topicos1.floricultura.dto.NivelToxicidadeResponseDTO;
import br.unitins.topicos1.floricultura.dto.PlantaCriarRascunhoDTO;
import br.unitins.topicos1.floricultura.dto.PlantaDTO;
import br.unitins.topicos1.floricultura.dto.PlantaResponseDTO;
import br.unitins.topicos1.floricultura.dto.PlantaUpdateAddRemoveQuantidadeDTO;
import br.unitins.topicos1.floricultura.dto.PlantaUpdateStatusPlantaDTO;
import br.unitins.topicos1.floricultura.dto.PortePlantaResponseDTO;
import br.unitins.topicos1.floricultura.dto.StatusPlantaResponseDTO;
import br.unitins.topicos1.floricultura.form.PlantaImageForm;
import br.unitins.topicos1.floricultura.model.StatusPlanta;
import jakarta.validation.Valid;

public interface PlantaService {

    // public PlantaResponseDTO insert(@Valid PlantaDTO dto);

    public PlantaResponseDTO criarRascunho(@Valid PlantaCriarRascunhoDTO dto);

    public PlantaResponseDTO update(@Valid PlantaDTO dto, Long id);

    public void delete(Long id);

    public List<PlantaResponseDTO> findAll();    

    public PlantaResponseDTO findById(Long id);

    public List<PlantaResponseDTO> findByNome(String nome);

    // public List<StatusPlantaResponseDTO> findAllStatusPlanta();
    // public List<NivelDificuldadeResponseDTO> findAllNivelDificuldade();
    // public List<NivelToxicidadeResponseDTO> findAllNivelToxicidade();
    // public List<PortePlantaResponseDTO> findAllPortePlanta();



    // public PlantaResponseDTO findByCodigo(String codigo);

    // public List<PlantaResponseDTO> findByTag(Long id);

    // public List<PlantaResponseDTO> findByFornecedor(Long id);

    public List<PlantaResponseDTO> findByStatusPlanta(Integer idStatusPlanta);

    // public List<PlantaResponseDTO> findByNivelDificuldade(Long id);

    // public List<PlantaResponseDTO> findByNivelToxidade(Long id);

    // public List<PlantaResponseDTO> findByPortePlanta(String porte);

    public PlantaResponseDTO adicionarImagem(PlantaImageForm form, Long id);

    public File downloadImagem(String nomeImagem, Long id);

    public void deleteImagem(String nomeImagem, Long id);

    public void definirImagemPrincipal(String nomeImagem, Long id);

    public void updateStatusPlanta(@Valid PlantaUpdateStatusPlantaDTO dto, Long id);

    public void updateAddRemoveQuantidade(@Valid PlantaUpdateAddRemoveQuantidadeDTO dto, Long id);


}
