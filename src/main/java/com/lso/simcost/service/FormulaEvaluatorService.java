package com.lso.simcost.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Configuration
public class FormulaEvaluatorService {

    private final SpelExpressionParser expressionParser;

    @Autowired
    public FormulaEvaluatorService(SpelExpressionParser expressionParser) {
        this.expressionParser = expressionParser;
    }

    public double evaluateFormula(String formula_cost, Map<String, Double> variables){
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.addPropertyAccessor(new MapAccessor());

        for (Map.Entry<String, Double> variableEntry : variables.entrySet()) {
            context.setVariable(variableEntry.getKey(), variableEntry.getValue());
        }

        Expression expression = expressionParser.parseExpression(formula_cost);
        Double result = expression.getValue(context, Double.class);
        return result != null ? result : 0.0;
    }
}
