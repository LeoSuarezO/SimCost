package com.lso.simcost.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.spel.standard.SpelExpressionParser;

@Configuration
public class SpringConfig {
    @Bean
    public SpelExpressionParser spelExpressionParser() {
        return new SpelExpressionParser();
    }
}
