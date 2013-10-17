package com.github.agiledon.sisyphus.composer.parser;

import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.stringtemplate.v4.ST;

import java.util.List;

import static com.github.agiledon.sisyphus.util.ResourceLoader.loadResource;
import static com.google.common.collect.Iterables.getFirst;
import static com.google.common.collect.Iterables.getLast;

public class StringTemplateParser {
    public static final char VARIABLE_INDICATOR = '$';
    private String templateFileName;

    public StringTemplateParser(String templateFileName) {
        this.templateFileName = templateFileName;
    }

    public String evaluate(List<String> variableContent) {
        String template = loadResource(templateFileName);
        ST st = new ST(template, VARIABLE_INDICATOR, VARIABLE_INDICATOR);

        List<Variable> variables = Lists.transform(variableContent, toVariable());
        for (Variable variableObj : variables) {
            st.add(variableObj.name, variableObj.value);
        }

        return st.render();
    }

    private Function<String, Variable> toVariable() {
        return new Function<String, Variable>() {
            @Override
            public Variable apply(String variable) {
                Iterable<String> variablePair = Splitter.on("=").trimResults().split(variable);
                return new Variable(getFirst(variablePair, "error_variable_name"), getLast(variablePair, " "));
            }
        };
    }

    private class Variable {
        public String name;
        public String value;

        private Variable(String name, String value) {
            this.name = name;
            this.value = value;
        }
    }
}
