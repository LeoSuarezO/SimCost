package com.lso.simcost.dto;

import com.lso.simcost.entities.Cost;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {

    private Cost cost;
    private  VariableDTO variable;

}
