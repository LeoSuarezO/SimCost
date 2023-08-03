package com.lso.simcost.repository;

import com.lso.simcost.entities.Cost;
import com.lso.simcost.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CostRepository extends JpaRepository<Cost, Integer> {

    @Query("SELECT c FROM Cost c WHERE c.cost_name = :cost_name")
    Optional<Cost> findCostByName(String cost_name);

    @Query("SELECT c FROM Cost c WHERE c.id_cost = :id_cost")
    Cost findCostById(Integer id_cost);

    @Query("SELECT c FROM Cost c WHERE c.id_project = :id_project")
    List<Cost> findCostByProject(Integer id_project);

    @Query("SELECT  p FROM  Project p WHERE  p.id_project = :id_project")
    Optional<Project> findProjectByID(Integer id_project);

}
