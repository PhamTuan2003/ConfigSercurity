package com.fpt.ezpark.vn.repository;

import com.fpt.ezpark.vn.model.entity.Possession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PossessionRepository extends JpaRepository<Possession, Long> {

    Possession findByName(String name);
}
