package com.github.agiledon.sisyphus.sis;

public class SisNormalClass extends SisClass {

    public SisNormalClass() {
        super();
    }

    public SisNormalClass(String fieldName) {
        super(fieldName);
    }

    protected void printEndIndicator(StringBuilder builder) {
        builder.append("}");
    }

    protected void printStartIndicator(StringBuilder builder) {
        builder.append("{");
    }
}
