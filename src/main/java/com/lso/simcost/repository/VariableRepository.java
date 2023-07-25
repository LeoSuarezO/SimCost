package com.lso.simcost.repository;

import com.lso.simcost.entities.VariableCost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VariableRepository extends JpaRepository<VariableCost, Integer> {
}
