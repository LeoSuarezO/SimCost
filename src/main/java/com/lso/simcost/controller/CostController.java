package com.lso.simcost.controller;

import com.lso.simcost.dto.VariableDTO;
import com.lso.simcost.entities.Cost;
import com.lso.simcost.service.CostService;
import com.lso.simcost.service.FormulaEvaluatorService;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cost")
@AllArgsConstructor
public class CostController {

    private final CostService service;
    private final FormulaEvaluatorService formulaEvaluatorService;
    private final RestTemplate restTemplate;
    @PostMapping
    public Cost saveCost(@RequestBody Cost cost){
        return service.saveCost(cost);
    }
    public boolean costExist(String cost_name){return service.exist(cost_name);}
    @PostMapping("/formula/{id_cost}/{formula_cost}")
    public ResponseEntity<String> createFormula(@PathVariable Integer id_cost, @PathVariable String formula_cost){
        List<VariableDTO>  variableDTOList = service.getListVariable(id_cost);
        if(variableDTOList.isEmpty()){
            return new ResponseEntity<>("No existen variables creadas, debe crear al menos una variable para crear la formula", HttpStatus.NO_CONTENT);
        }else{
            service.createFormula(id_cost, formula_cost);
        }
        return new ResponseEntity<>("Formula creada exitosamente", HttpStatus.CREATED);
    }

    @GetMapping("/{id_cost}")
    public ResponseEntity<Double> calculateCost(@PathVariable Integer id_cost){
        Cost cost = service.getCost(id_cost);
        Double result = null;
        Map<String, Double> variables = variableContext(id_cost);
        if(cost.getFormula_cost() != null){
            result = formulaEvaluatorService.evaluateFormula(cost.getFormula_cost(), variables);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(result, HttpStatus.EXPECTATION_FAILED);
        }

    }
    @PostMapping("/{name_variable}/{value}/{id_cost}")
    public VariableDTO setValueVariable(@PathVariable String name_variable, @PathVariable Double value,@PathVariable Integer id_cost){
        List<VariableDTO> variableDTOList = service.getListVariable(id_cost);
        VariableDTO variableDTOTemp = null;
        for (VariableDTO variableDTO : variableDTOList) {
            if(variableDTO.getName_variable().equalsIgnoreCase(name_variable)){
                variableDTOTemp = service.setValueVariable(name_variable, value);
            }
        }
        return variableDTOTemp;
    }

    public Map<String, Double> variableContext(Integer id_cost){
        Cost cost = service.getCost(id_cost);
        Map<String, Double> variables = new HashMap<>();
        List<VariableDTO> variableDTOList = service.getListVariable(cost.getId_cost());
        for (VariableDTO variableDTO : variableDTOList) {
            variables.put(variableDTO.getName_variable(), variableDTO.getValue());
        }
        return variables;
    }
}
