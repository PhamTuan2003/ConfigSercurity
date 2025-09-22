package com.fpt.ezpark.vn.service.impl;

import com.fpt.ezpark.vn.model.entity.Possession;
import com.fpt.ezpark.vn.repository.PossessionRepository;
import com.fpt.ezpark.vn.service.PossessionService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PossessionServiceImpl implements PossessionService {

    private final PossessionRepository possessionRepository;

    @Override
    public Possession findByName(String name) {
        return possessionRepository.findByName(name);
    }

    @Override
    public Possession findOne(Long id) {
        return possessionRepository.findById(id).orElse(null);
    }

    @Override
    public List<Possession> findAll() {
        return possessionRepository.findAll();
    }

    @Override
    public Possession save(Possession possession) {
        return possessionRepository.save(possession);
    }
}
