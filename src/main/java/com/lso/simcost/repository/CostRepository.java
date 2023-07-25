package com.lso.simcost.repository;

import com.lso.simcost.entities.Cost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CostRepository extends JpaRepository<Cost, Integer> {}
