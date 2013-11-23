package com.github.agiledon.sisyphus.sis.rule;

import com.github.agiledon.sisyphus.sis.SisClass;
import com.github.agiledon.sisyphus.sis.SisNormalClass;

public class SisNormalClassRule extends SisClassRule {

    public boolean match(String line) {
        return line.contains("{");
    }

    protected SisClass createSisClass(String line) {
        if (isNestedClass(line)) {
            return new SisNormalClass();
        }
        return new SisNormalClass(getFieldName(line));
    }

    @Override
    public SisClass generate() {
        return new SisNormalClass();
    }
}
