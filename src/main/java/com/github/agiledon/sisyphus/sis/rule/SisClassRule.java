package com.github.agiledon.sisyphus.sis.rule;

import com.github.agiledon.sisyphus.sis.SisClass;

public abstract class SisClassRule extends ParsingRule implements SisClassGenerator {
    @Override
    public SisClass sisClassNode(SisClass currentClass, String line) {
        if (isMainClass(line)) {
            return currentClass;
        }
        return navigateToChild(currentClass, line);
    }

    private boolean isMainClass(String line) {
        return line.startsWith("{");
    }

    protected SisClass navigateToChild(SisClass currentClass, String line) {
        SisClass childProperty = createSisClass(line);
        currentClass.addChildClass(childProperty);
        return childProperty;
    }

    protected abstract SisClass createSisClass(String line);

    protected String getFieldName(String line) {
        return line.split("=")[0].trim();
    }

    protected boolean isNestedClass(String line) {
        return line.length() != line.trim().length() && line.trim().startsWith("{");
    }
}
