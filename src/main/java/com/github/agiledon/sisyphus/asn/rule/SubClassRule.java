package com.github.agiledon.sisyphus.asn.rule;

import com.github.agiledon.sisyphus.asn.AsnClass;

public abstract class SubClassRule extends ParsingRule implements AsnClassGenerator {
    @Override
    public AsnClass generate(AsnClass currentClass, String line) {
        return navigateToChild(currentClass, line);
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

    protected boolean noFieldName(String line) {
        return line.trim().startsWith("{");
    }
}
