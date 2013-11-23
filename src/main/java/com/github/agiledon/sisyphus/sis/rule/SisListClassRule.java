package com.github.agiledon.sisyphus.sis.rule;

import com.github.agiledon.sisyphus.sis.SisClass;
import com.github.agiledon.sisyphus.sis.SisListClass;

public class SisListClassRule extends SisClassRule {
    public static final String LIST_INDICATOR = "<";

    @Override
    protected SisClass createSisClass(String line) {
        if (isNestedClass(line)) {
            return new SisListClass();
        }
        return new SisListClass(getFieldName(line));
    }

    @Override
    public boolean match(String line) {
        return line.contains(LIST_INDICATOR);
    }

    @Override
    public SisClass generate() {
        return new SisListClass();
    }
}
