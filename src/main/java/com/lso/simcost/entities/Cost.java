package com.lso.simcost.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cost {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id_cost;
    private Integer id_project;
    private String cost_name;
    private String formula_cost;

}
