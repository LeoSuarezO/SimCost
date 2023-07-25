package com.lso.simcost.service;

import com.lso.simcost.entities.Cost;
import com.lso.simcost.repository.CostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CostService {
    private final CostRepository repository;

    public Cost saveCost(Cost cost){
        return  repository.save(cost);
    }


}
