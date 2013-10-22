package com.github.agiledon.sisyphus.asn.rule;

import com.github.agiledon.sisyphus.asn.AsnClass;
import com.github.agiledon.sisyphus.asn.AsnSequenceClass;
import com.github.agiledon.sisyphus.asn.AsnVectorClass;

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

    protected AsnClass createAsnClass(String line) {
        if (new NestedClassRule().match(line)) {
            return new AsnSequenceClass();
        }
        String fieldName = getFieldName(line.split("="));
        if (isVector(line)) {
            return new AsnVectorClass(fieldName);
        }
        return new AsnSequenceClass(fieldName);
    }

    private boolean isVector(String line) {
        return line.contains(VECTOR_INDICATOR);
    }

    private String getFieldName(String[] split) {
        return split[0].trim();
    }
}
