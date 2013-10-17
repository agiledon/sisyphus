package com.github.agiledon.sisyphus.composer.parser;

import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.stringtemplate.v4.ST;

import java.util.Arrays;
import java.util.List;

import static com.github.agiledon.sisyphus.util.ResourceLoader.loadResource;

public class StringTemplateParser {
    public static final char VARIABLE_INDICATOR = '$';
    private String templateFileName;

    public StringTemplateParser(String templateFileName) {
        this.templateFileName = templateFileName;
    }

    public String evaluate(List<String> variableContent) {
        String template = loadResource(templateFileName);
        ST st = new ST(template, VARIABLE_INDICATOR, VARIABLE_INDICATOR);

        List<Variable> variables = Lists.transform(variableContent, new Function<String, Variable>() {
            @Override
            public Variable apply(String variable) {
                String[] variablePair = variable.split("=");
                return new Variable(variablePair[0].trim(), variablePair[1].trim());
            }
        });

        for (Variable variableObj : variables) {
            st.add(variableObj.name, variableObj.value);
        }

        return st.render();
    }

    private class Variable {
        public String name;
        public Object value;

        private Variable(String name, Object value) {
            this.name = name;
            this.value = value;
        }
    }
}
