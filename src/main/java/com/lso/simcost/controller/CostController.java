package com.lso.simcost.controller;

import com.lso.simcost.entities.Cost;
import com.lso.simcost.service.CostService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cost")
@AllArgsConstructor
public class CostController {

    private final CostService service;
    @PostMapping
    public Cost saveCost(@RequestBody Cost cost){
        return service.saveCost(cost);
    }
}
