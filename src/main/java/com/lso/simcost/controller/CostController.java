package com.lso.simcost.controller;

import com.lso.simcost.dto.VariableDTO;
import com.lso.simcost.entities.Cost;
import com.lso.simcost.entities.CostVariable;
import com.lso.simcost.service.CostService;
import com.lso.simcost.service.FormulaEvaluatorService;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/cost")
@AllArgsConstructor
public class CostController {

    private final CostService service;
    private final FormulaEvaluatorService formulaEvaluatorService;

    @PostMapping
    public ResponseEntity<Cost> saveCost(@RequestBody Cost cost) {
        if (service.exist(cost.getCost_name())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(service.saveCost(cost), HttpStatus.CREATED);
    }

    @GetMapping("/costList={id_project}")
    public ResponseEntity<List<Cost>> findAllCost(@PathVariable Integer id_project) {
        if(service.projectExist(id_project).isPresent()){
            List<Cost> costList = service.findAllCost(id_project);
            if(costList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(service.findAllCost(id_project), HttpStatus.OK);
        }
        return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/deleteCost")
    public ResponseEntity<String> deleteCost(@RequestBody Cost cost) {
        Optional<Cost> costOptional = service.findById(cost.getId_cost());
        if (costOptional.isPresent()) {
            service.deleteCost(cost.getId_cost());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/updateCostName")
    public ResponseEntity<Cost> updateCostName(@RequestBody Cost cost) {
        Optional<Cost> costOptional = service.findById(cost.getId_cost());
        if (costOptional.isPresent()) {
            if(!service.exist(cost.getCost_name())){
                return new ResponseEntity<>(service.updateCostName(cost.getId_cost(), cost.getCost_name()), HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/formula")
    public ResponseEntity<String> createFormula(@RequestBody Cost cost) {
        Optional<Cost> costOptional = service.findById(cost.getId_cost());
        if (costOptional.isPresent()) {
            List<VariableDTO> variableDTOList = service.getListVariable(cost.getId_cost());
            if (variableDTOList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                service.createFormula(cost.getId_cost(), cost.getFormula_cost());
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/calculateCost")
    public ResponseEntity<Double> calculateCost(@RequestBody CostVariable costVariable) {
        Optional<Cost> optionalCost = service.findById(costVariable.getCost().getId_cost());
        if (optionalCost.isPresent()) {
            Cost cost = optionalCost.get();
            List<VariableDTO> variableDTOList = costVariable.getVariableDTOList();
            service.setValueVariable(cost.getId_cost(), variableDTOList);
            Map<String, Double> variables = service.variableContext(cost.getId_cost());
            if (cost.getFormula_cost() != null) {
                return formulaEvaluatorService.evaluateFormula(cost.getFormula_cost(), variables);
            } else {
                return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/variable")
    public ResponseEntity<VariableDTO> crateVariable(@RequestBody VariableDTO variableDTO) {
       return service.createVariable(variableDTO);
    }

    @GetMapping("/variable/listVariable={id_cost}")
    public ResponseEntity<List<VariableDTO>> findAllVariable(@PathVariable Integer id_cost) {
        if(service.findById(id_cost).isPresent()){
            return new ResponseEntity<>(service.getListVariable(id_cost), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/variable/update")
    public ResponseEntity<VariableDTO> updateVariable(@RequestBody VariableDTO variableDTO){
        return service.updateVariable(variableDTO);
    }

    @PostMapping("/variable/delete")
    public ResponseEntity<VariableDTO> deleteVariable(@RequestBody VariableDTO variableDTO){
        return service.deleteVariable(variableDTO);
    }
}
