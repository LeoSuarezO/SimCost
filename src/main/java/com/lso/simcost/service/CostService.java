package com.lso.simcost.service;

import com.lso.simcost.dto.VariableDTO;
import com.lso.simcost.entities.Cost;
import com.lso.simcost.exception.CostNotFound;
import com.lso.simcost.repository.CostRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CostService {
    private final CostRepository repository;
    private final RestTemplate restTemplate;

    public Cost saveCost(Cost cost){
        return  repository.save(cost);
    }

    public List<VariableDTO> getListVariable(Integer id_cost){
        ResponseEntity<List<VariableDTO>>  responseEntity = restTemplate.exchange("http://localhost:8081/api/variable/{id_cost}", HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {}, id_cost);
        return responseEntity.getBody();
    }

    public VariableDTO setValueVariable(String name_variable, Double value){
        ResponseEntity<VariableDTO>  responseEntity = restTemplate.exchange("http://localhost:8081/api/variable/{name_variable}/{value}", HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {}, name_variable, value);
        return responseEntity.getBody();
    }
    public boolean exist(String cost_name){
        Optional<Cost> costOptional = repository.findCostByName(cost_name);
        return costOptional.isPresent();
    }

    public Cost getCost(Integer id_cost){
        return repository.findCostById(id_cost);
    }

    public void createFormula(Integer id_cost, String formula_cost){
        Optional<Cost> optionalCost = repository.findById(id_cost);
        if(optionalCost.isPresent()){
            Cost cost = optionalCost.get();
            cost.setFormula_cost(formula_cost);
            repository.save(cost);
        }else {
            throw new CostNotFound("El costo con ID " + id_cost + " no fue encontrado");
        }
    }

}
