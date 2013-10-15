package com.github.agiledon.sisyphus.composer.parser;

import org.stringtemplate.v4.ST;

import java.util.List;

import static com.github.agiledon.sisyphus.util.ResourceLoader.loadResource;

public class StringTemplateParser {
    private String templateFileName;

    public StringTemplateParser(String templateFileName) {
        this.templateFileName = templateFileName;
    }

    public String evaluate(List<String> variableContent) {
        String template = loadResource(templateFileName);
        ST st = new ST(template);

        //format: <variableName> = variableValue
        for (String variablePair : variableContent) {
             Variable variable = parseVariable(variablePair);
            st.add(variable.name, variable.value);
        }

        return st.render();
    }

    private Variable parseVariable(String variablePair) {
        String[] split = variablePair.split("=");
        String variableName = split[0].trim().replace("<", "").replace(">", "");
        String variableValue = split[1].trim();
        return new Variable(variableName, variableValue);
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
