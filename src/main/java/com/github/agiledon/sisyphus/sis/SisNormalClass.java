package com.github.agiledon.sisyphus.sis;

import com.github.agiledon.sisyphus.util.StringUtil;

import java.util.List;

public class SisNormalClass extends SisClass {

    public SisNormalClass() {
        super();
    }

    public SisNormalClass(String fieldName) {
        super(fieldName);
    }

    protected void printEnd(StringBuilder builder) {
        printLeftPadding(builder, false);
        builder.append("}");
    }

    protected void printStartIndicator(StringBuilder builder) {
        builder.append("{\n");
    }
}
