package com.hezk.security.controller;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("api")
public class ApiController {

    @Autowired
    private BeanFactory beanFactory;

    @GetMapping("/index")
    @RolesAllowed("")
    public String index() {

        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext evaluationContext = new StandardEvaluationContext();
        evaluationContext.setBeanResolver(new BeanFactoryResolver(beanFactory));
        System.out.println(parser.parseExpression("@person.say()").getValue(evaluationContext));

        return "index";
    }


}
