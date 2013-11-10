package com.github.agiledon.sisyphus.junit;

import com.github.agiledon.sisyphus.Fixture;
import com.github.agiledon.sisyphus.exception.NotSupportedException;import com.google.common.base.Strings;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.List;

public class DataProvider implements TestRule {

    private String resourceName;
    private Class<?> targetClass;
    private String templateName;

    @Override
    public Statement apply(final Statement statement, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                DataResource dataResource = description.getAnnotation(DataResource.class);
                resourceName = dataResource.resourceName();
                templateName = dataResource.templateName();
                targetClass = dataResource.targetClass();
                statement.evaluate();
            }
        };
    }

    @SuppressWarnings("unchecked")
    public <T> T provideData() {
        if (Strings.isNullOrEmpty(templateName)) {
            return (T) Fixture.from(resourceName)
                    .to(targetClass);
        } else {
            return (T) Fixture.from(resourceName)
                    .withTemplate(templateName)
                    .to(targetClass);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> provideDataList() {
        if (Strings.isNullOrEmpty(templateName)) {
            throw new NotSupportedException("Must provide template file name");
        } else {
            return (List<T>) Fixture.from(resourceName)
                    .withTemplate(templateName)
                    .toList(targetClass);
        }
    }
}
