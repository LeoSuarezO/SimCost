package com.lso.simcost.entities;

import com.lso.simcost.dto.VariableDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CostVariable {
    private List<VariableDTO> variableDTOList;
    private Cost cost;
}
