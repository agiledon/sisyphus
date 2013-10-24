package com.github.agiledon.sisyphus.asn.rule;

import com.github.agiledon.sisyphus.asn.AsnClass;

public class EndingIndicatorRule extends ParsingRule {
    public boolean match(String line) {
        return line.trim().startsWith("}");
    }

    @Override
    public AsnClass generate(AsnClass currentClass, String line) {
        return navigateToParent(currentClass);
    }

    private AsnClass navigateToParent(AsnClass currentClass) {
        AsnClass parentAsnClass = currentClass.getParentClass();
        if (parentAsnClass != null) {
            currentClass = parentAsnClass;
        }
        return currentClass;
    }
}
