package com.lso.simcost.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "cost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VariableCost> variable = new ArrayList<>();
}
