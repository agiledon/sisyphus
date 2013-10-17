package com.github.agiledon.sisyphus.asn;

import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;

public class SyntaxParser {
    public static final String EQUAL_OPERATOR = "=";
    public static final String VECTOR_INDICATOR = "SEQUENCE OF";
    public static final String LINE_BREAK = "\n";

    public ClassProperty parseClass(String content) {
        ClassProperty currentProperty = new ClassProperty();
        Iterable<String> lines = Splitter.on(LINE_BREAK)
                .omitEmptyStrings()
                .split(content);

        for (String line : filterSpaceLines(lines)) {
            currentProperty = setClassProperty(currentProperty, line);
        }
        return currentProperty;
    }

    private Iterable<String> filterSpaceLines(Iterable<String> lines) {
        return Iterables.filter(lines, new Predicate<String>() {
            @Override
            public boolean apply(String line) {
                return !Strings.isNullOrEmpty(line.trim());
            }
        });
    }

    private ClassProperty setClassProperty(ClassProperty currentProperty, String line) {
        if (!isMainClass(line)) {
            if (endOfClass(line)) {
                currentProperty = navigateToParent(currentProperty);
            } else {
                if (isClassField(line) || isNestedClass(line)) {
                    currentProperty = navigateToChild(currentProperty, line);
                }else {
                    currentProperty.addBasicField(parseBasicField(line));
                }
            }
        }
        return currentProperty;
    }

    protected boolean isSequence(String line) {
        return line.contains("SEQUENCE") || line.contains("CHOICE");
    }

    protected boolean isMainClass(String line) {
        return line.startsWith("{");
    }

    protected BasicField parseBasicField(String line) {
        String[] split = line.split(EQUAL_OPERATOR);
        if (split.length == 1) {
            return new BasicField(getFieldName(split), "");
        }
        return new BasicField(getFieldName(split), getFieldValue(split));
    }

    protected ClassProperty parseClassProperty(String line) {
        if (isNestedClass(line)) {
            return new ClassProperty();
        }
        String fieldName = getFieldName(line.split(EQUAL_OPERATOR));
        return new ClassProperty(fieldName, isVector(line));
    }

    protected boolean isClassField(String line) {
        return isSequence(line) && line.contains(EQUAL_OPERATOR);
    }

    private boolean isVector(String line) {
        return line.contains(VECTOR_INDICATOR);
    }

    private String getFieldValue(String[] split) {
        String secondPart = split[1].trim();
        if (secondPart.endsWith(",")) {
            secondPart = secondPart.replace(",", "");
        }
        return secondPart;
    }

    private String getFieldName(String[] split) {
        return split[0].trim();
    }

    private ClassProperty navigateToChild(ClassProperty currentProperty, String line) {
        ClassProperty childProperty = parseClassProperty(line);
        currentProperty.addChildClassProperty(childProperty);

        currentProperty = childProperty;
        return currentProperty;
    }

    protected boolean isNestedClass(String line) {
        return line.length() != line.trim().length() && line.trim().startsWith("{");
    }

    private ClassProperty navigateToParent(ClassProperty currentProperty) {
        ClassProperty parentClassProperty = currentProperty.getParentClassProperty();
        if (parentClassProperty != null) {
            currentProperty = parentClassProperty;
        }
        return currentProperty;
    }

    protected boolean endOfClass(String line) {
        return line.trim().contains("}");
    }
}
