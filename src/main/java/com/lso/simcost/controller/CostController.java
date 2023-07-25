package com.lso.simcost.controller;

import com.lso.simcost.dto.VariableDTO;
import com.lso.simcost.entities.Cost;
import com.lso.simcost.service.CostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cost")
@AllArgsConstructor
public class CostController {

    private final CostService service;
    @PostMapping
    public Cost saveCost(@RequestBody Cost cost){
        return service.saveCost(cost);
    }
    public boolean costExist(String cost_name){return service.exist(cost_name);}
    @PostMapping("/formula")
    public ResponseEntity<String> createFormula(Integer id_cost, String formula){
        List<VariableDTO>  variableDTOList = service.getListVariable(id_cost);
        if(variableDTOList.isEmpty()){
            return new ResponseEntity<>("No existen variables creadas, debe crear al menos una variable para crear la formula", HttpStatus.NO_CONTENT);
        }else{
            service.createFormula(id_cost, formula);
        }
        return new ResponseEntity<>("Formula creada exitosamente", HttpStatus.CREATED);
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
    public Double calculateCost(){
        return null;
    }
}
