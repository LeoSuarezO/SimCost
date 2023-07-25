package com.lso.simcost.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.expression.MapAccessor;
import org.springframework.data.repository.query.SpelEvaluator;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Data
public class FormulaEvaluatorService {

    private SpelExpressionParser expressionParser;
    public double evaluateFormula(String formula, Map<String, Double> variables){
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.addPropertyAccessor(new MapAccessor());

        for (Map.Entry<String, Double> variableEntry : variables.entrySet()) {
            context.setVariable(variableEntry.getKey(), variableEntry.getValue());
        }

        Expression expression = expressionParser.parseExpression(formula);
        Double result = expression.getValue(context, Double.class);
        return result != null ? result : 0.0;
    }
}
