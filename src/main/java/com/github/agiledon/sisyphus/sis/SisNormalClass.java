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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        printStart(builder);
        printBasicFields(builder);
        printChildClasses(builder);
        printEndIndicator(builder);
        return builder.toString();
    }

    private void printEndIndicator(StringBuilder builder) {
        printLeftPadding(builder);
        builder.append("}");
    }

    private void printBasicFields(StringBuilder builder) {
        List<BasicField> basicFields = getBasicFields();
        for (int i = 0; i < basicFields.size(); i++) {
            builder.append(StringUtil.spaces((getLevel() + 1) * TAB_SPACES_COUNT));
            builder.append(basicFields.get(i).toString());
            if (i == basicFields.size() - 1) {
                builder.append("\n");
            }
        }
    }

    protected void printStartIndicator(StringBuilder builder) {
        builder.append("{\n");
    }
}
