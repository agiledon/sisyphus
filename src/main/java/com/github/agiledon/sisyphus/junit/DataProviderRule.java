package com.github.agiledon.sisyphus.junit;

import com.github.agiledon.sisyphus.Fixture;
import com.google.common.base.Strings;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class DataProviderRule implements TestRule {

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
}
