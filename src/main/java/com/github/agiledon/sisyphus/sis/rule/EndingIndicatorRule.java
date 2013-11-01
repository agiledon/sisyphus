package com.github.agiledon.sisyphus.sis.rule;

import com.github.agiledon.sisyphus.sis.SisClass;

public class EndingIndicatorRule extends ParsingRule {
    public boolean match(String line) {
        return line.trim().startsWith("}") || line.trim().startsWith("]");
    }

    @Override
    public SisClass asnClassNode(SisClass currentClass, String line) {
        return navigateToParent(currentClass);
    }

    private SisClass navigateToParent(SisClass currentClass) {
        SisClass parentSisClass = currentClass.getParentClass();
        if (parentSisClass != null) {
            return parentSisClass;
        }
        return currentClass;
    }
}
