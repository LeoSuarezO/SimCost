package com.lso.simcost.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VariableCost {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id_variable;
    private String name_variable;
    private String type_variable;
    private Double value;

    @ManyToOne
    @JoinColumn(name = "id_cost")
    private Cost cost;

}
