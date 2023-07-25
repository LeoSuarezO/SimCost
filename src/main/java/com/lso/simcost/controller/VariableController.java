package com.lso.simcost.controller;

import com.lso.simcost.entities.VariableCost;
import com.lso.simcost.service.VariableService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cost/variable")
@AllArgsConstructor
public class VariableController {

    private final VariableService service;

    public VariableCost saveVariable(@RequestBody VariableCost variableCost){
        return service.saveVariable(variableCost);
    }
}
