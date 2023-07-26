package com.lso.simcost.controller;

import com.lso.simcost.dto.VariableDTO;
import com.lso.simcost.entities.Cost;
import com.lso.simcost.entities.CostVariable;
import com.lso.simcost.exception.CostExist;
import com.lso.simcost.exception.VariableExist;
import com.lso.simcost.service.CostService;
import com.lso.simcost.service.FormulaEvaluatorService;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Var;
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

    @PostMapping
    public Cost saveCost(@RequestBody Cost cost) {
        if (costExist(cost.getCost_name())) {
            throw new CostExist("El costo que intenta crear ya existe.");
        }
        return service.saveCost(cost);
    }

    @PostMapping("/formula")
    public ResponseEntity<String> createFormula(@RequestBody Cost cost) {
        List<VariableDTO> variableDTOList = service.getListVariable(cost.getId_cost());
        if (variableDTOList.isEmpty()) {
            return new ResponseEntity<>("No existen variables creadas, debe crear al menos una variable para crear la formula", HttpStatus.NO_CONTENT);
        } else {
            service.createFormula(cost.getId_cost(), cost.getFormula_cost());
        }
        return new ResponseEntity<>("Formula was created", HttpStatus.CREATED);
    }

    @PostMapping("/calculateCost")
    public ResponseEntity<String> calculateCost(@RequestBody CostVariable costVariable) {
        Cost cost = service.getCost(costVariable.getCost().getId_cost());
        System.out.println(cost.toString());
        List<VariableDTO> variableDTOList = costVariable.getVariableDTOList();
        setValueVariable(cost.getId_cost(), variableDTOList);
        Map<String, Double> variables = variableContext(cost.getId_cost());
        if (cost.getFormula_cost() != null) {
            double result = formulaEvaluatorService.evaluateFormula(cost.getFormula_cost(), variables);
            return new ResponseEntity<>("El calculo del costo es: "+result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("El costo no contiene ninguna formula creada.", HttpStatus.EXPECTATION_FAILED);
        }
    }

    public void setValueVariable(Integer id_cost, List<VariableDTO> variableDTOList) {
        List<VariableDTO> compareVariableList = service.getListVariable(id_cost);
        for (VariableDTO variableDTO : variableDTOList) {
            for (VariableDTO variableDTOTemp : compareVariableList) {
                if (variableDTO.getName_variable().equalsIgnoreCase(variableDTOTemp.getName_variable())) {
                    service.setValueVariable(variableDTO.getName_variable(), variableDTO.getValue());
                }

            }
        }
    }
        @PostMapping("/variable")
        public VariableDTO crateVariable (@RequestBody VariableDTO variableDTO){
            if (variableExist(variableDTO.getId_cost(), variableDTO.getName_variable())) {
                throw new VariableExist("La variable que intenta crear ya existe!");
            }
            return service.createVariable(variableDTO);
        }

        public Map<String, Double> variableContext (Integer id_cost){
            Cost cost = service.getCost(id_cost);
            Map<String, Double> variables = new HashMap<>();
            List<VariableDTO> variableDTOList = service.getListVariable(cost.getId_cost());
            for (VariableDTO variableDTO : variableDTOList) {
                variables.put(variableDTO.getName_variable(), variableDTO.getValue());
            }
            return variables;
        }

        public boolean variableExist (Integer id_cost, String name_vatiable){
            List<VariableDTO> variableDTOList = service.getListVariable(id_cost);
            for (VariableDTO variableDTO : variableDTOList) {
                if (variableDTO.getName_variable().equalsIgnoreCase(name_vatiable)) {
                    return true;
                }
            }
            return false;
        }

        public boolean costExist (String cost_name){
            return service.exist(cost_name);
        }
    }
