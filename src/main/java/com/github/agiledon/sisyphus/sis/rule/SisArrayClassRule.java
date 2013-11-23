package com.github.agiledon.sisyphus.sis.rule;

import com.github.agiledon.sisyphus.sis.SisArrayClass;
import com.github.agiledon.sisyphus.sis.SisClass;

public class SisArrayClassRule extends SisClassRule {
    public static final String ARRAY_INDICATOR = "[";

    @Override
    protected SisClass createSisClass(String line) {
        if (isNestedClass(line)) {
            return new SisArrayClass();
        }
        return new SisArrayClass(getFieldName(line));
    }

    @Override
    public boolean match(String line) {
        return line.contains(ARRAY_INDICATOR);
    }

    @Override
    public SisClass generate() {
        return new SisArrayClass();
    }
}
