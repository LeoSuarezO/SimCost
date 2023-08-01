package com.lso.simcost.service;

import com.lso.simcost.dto.VariableDTO;
import com.lso.simcost.entities.Cost;
import com.lso.simcost.repository.CostRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CostService {
    private final CostRepository repository;
    private final RestTemplate restTemplate;

    public Cost saveCost(Cost cost) {
        return repository.save(cost);
    }

    public List<Cost> findAllCost(Integer id_project) {
        return repository.findCostByProject(id_project);
    }

    public void deleteCost(Integer id_cost) {
        repository.deleteById(id_cost);
    }

    public Optional<Cost> findById(Integer id_cost) {
        return repository.findById(id_cost);
    }

    public List<VariableDTO> getListVariable(Integer id_cost) {
        ResponseEntity<List<VariableDTO>> responseEntity =
                restTemplate.exchange("http://localhost:8081/api/variable/{id_cost}",
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {}, id_cost);
        return responseEntity.getBody();
    }

    public void sendValueVariable(VariableDTO variableDTO) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<VariableDTO> requestEntity = new HttpEntity<>(variableDTO, headers);
        restTemplate.exchange("http://localhost:8081/api/variable/updateValue",
                HttpMethod.POST, requestEntity, VariableDTO.class);
    }

    public ResponseEntity<VariableDTO> createVariable(VariableDTO variableDTO) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<VariableDTO> requestEntity = new HttpEntity<>(variableDTO, headers);
        return restTemplate.exchange("http://localhost:8081/api/variable",
                HttpMethod.POST, requestEntity, VariableDTO.class);
    }

    public ResponseEntity<VariableDTO> updateVariable(VariableDTO variableDTO) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<VariableDTO> requestEntity = new HttpEntity<>(variableDTO, headers);
        return restTemplate.exchange("http://localhost:8081/api/variable/updateVariable",
                HttpMethod.POST, requestEntity, VariableDTO.class);
    }

    public ResponseEntity<VariableDTO> deleteVariable(VariableDTO variableDTO){
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<VariableDTO> requestEntity = new HttpEntity<>(variableDTO, headers);
        return restTemplate.exchange("http://localhost:8081/api/variable/deleteteVariable",
                HttpMethod.POST, requestEntity, VariableDTO.class);
    }

    public boolean exist(String cost_name) {
        Optional<Cost> costOptional = repository.findCostByName(cost_name);
        return costOptional.isPresent();
    }

    public Cost getCost(Integer id_cost) {
        return repository.findCostById(id_cost);
    }

    public void createFormula(Integer id_cost, String formula_cost) {
        Cost cost = getCost(id_cost);
        cost.setFormula_cost(formula_cost);
        repository.save(cost);
    }

    public Cost updateCostName(Integer id_cost, String name) {
        Cost cost = getCost(id_cost);
        cost.setCost_name(name);
        repository.save(cost);
        return cost;
    }

    public void setValueVariable(Integer id_cost, List<VariableDTO> variableDTOList) {
        List<VariableDTO> compareVariableList = getListVariable(id_cost);
        for (VariableDTO variableDTO : variableDTOList) {
            for (VariableDTO variableDTOTemp : compareVariableList) {
                if (variableDTO.getName_variable().equalsIgnoreCase(variableDTOTemp.getName_variable())) {
                    variableDTO.setId_cost(id_cost);
                    sendValueVariable(variableDTO);
                }
            }
        }
    }

    public Map<String, Double> variableContext(Integer id_cost) {
        Cost cost = getCost(id_cost);
        Map<String, Double> variables = new HashMap<>();
        List<VariableDTO> variableDTOList = getListVariable(cost.getId_cost());
        for (VariableDTO variableDTO : variableDTOList) {
            variables.put(variableDTO.getName_variable(), variableDTO.getValue());
        }
        return variables;
    }
}
