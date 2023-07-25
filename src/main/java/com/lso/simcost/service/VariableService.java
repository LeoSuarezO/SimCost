package com.lso.simcost.service;

import com.lso.simcost.entities.VariableCost;
import com.lso.simcost.repository.VariableRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VariableService {
    private VariableRepository repository;

    public VariableCost saveVariable(VariableCost variableCost){
        return repository.save(variableCost);
    }
}
