package com.github.agiledon.sisyphus.asn.rule;

import com.github.agiledon.sisyphus.asn.AsnClass;

public abstract class AsnClassRule extends ParsingRule implements AsnClassGenerator {
    @Override
    public AsnClass generate(AsnClass currentClass, String line) {
        if (isMainClass(line)) {
            return currentClass;
        }
        return navigateToChild(currentClass, line);
    }

    private boolean isMainClass(String line) {
        return line.startsWith("{");
    }

    protected AsnClass navigateToChild(AsnClass currentClass, String line) {
        AsnClass childProperty = createAsnClass(line);
        currentClass.addChildClass(childProperty);

        currentClass = childProperty;
        return currentClass;
    }

    protected abstract AsnClass createAsnClass(String line);

    protected String getFieldName(String line) {
        return line.split("=")[0].trim();
    }

    protected boolean isNestedClass(String line) {
        return line.length() != line.trim().length() && line.trim().startsWith("{");
    }
}
