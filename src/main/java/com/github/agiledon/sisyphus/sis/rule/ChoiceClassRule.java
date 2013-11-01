package com.github.agiledon.sisyphus.sis.rule;

import com.github.agiledon.sisyphus.sis.SisChoiceClass;
import com.github.agiledon.sisyphus.sis.SisClass;

public class ChoiceClassRule extends SisClassRule {

    public boolean match(String line) {
        return line.contains("CHOICE");
    }

    protected SisClass createAsnClass(String line) {
        if (isNestedClass(line)) {
            return new SisChoiceClass();
        }
        return new SisChoiceClass(getFieldName(line));
    }

    @Override
    public SisClass generate() {
        return new SisChoiceClass();
    }
}
