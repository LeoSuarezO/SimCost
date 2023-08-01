package com.lso.simcost.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VariableDTO {

    private Integer id_variable;
    private Integer id_cost;
    private String name_variable;
    private String type_variable;
    private Double value;
    private Double defect_value;
}
