package com.fpt.ezpark.vn.service;

import com.fpt.ezpark.vn.model.entity.Possession;

import java.util.List;

public interface PossessionService {

    Possession findByName(String name);

    Possession findOne(Long id);

    List<Possession> findAll();

    Possession save(Possession possession);
}
