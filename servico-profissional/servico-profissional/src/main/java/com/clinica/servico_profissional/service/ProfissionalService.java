package com.clinica.servico_profissional.service;

import com.clinica.servico_profissional.model.Profissional;
import com.clinica.servico_profissional.repository.ProfissionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfissionalService {

    private final ProfissionalRepository profissionalRepository;

    public List<Profissional> listarAtivos() {
        return profissionalRepository.findByAtivoTrue();
    }

    public Profissional buscarPorId(Long id) {
        return profissionalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profissional nao encontrado"));
    }
}