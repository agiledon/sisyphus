package com.github.agiledon.sisyphus.sis;

import com.github.agiledon.sisyphus.util.StringUtil;

import java.util.List;

public class SisNormalClass extends SisClass {

    public static final int TAB_SPACES_COUNT = 4;

    public SisNormalClass() {
        super();
    }

    public SisNormalClass(String fieldName) {
        super(fieldName);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        printFieldName(builder);
        printStartIndicator(builder);
        printBasicFields(builder);
        printChildClasses(builder);
        printEndIndicator(builder);
        return builder.toString();
    }

    private void printEndIndicator(StringBuilder builder) {
        builder.append("}");
    }

    private void printBasicFields(StringBuilder builder) {
        List<BasicField> basicFields = getBasicFields();
        for (int i = 0; i < basicFields.size(); i++) {
            builder.append(StringUtil.spaces(TAB_SPACES_COUNT));
            builder.append(basicFields.get(i).toString());
            if (i == basicFields.size() - 1) {
                builder.append("\n");
            }
        }
    }

    private void printStartIndicator(StringBuilder builder) {
        builder.append("{\n");
    }
}
