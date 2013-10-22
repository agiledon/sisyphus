package com.github.agiledon.sisyphus.asn;

import com.google.common.base.*;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.List;

import static com.google.common.base.CharMatcher.anyOf;
import static com.google.common.collect.Iterables.getFirst;
import static com.google.common.collect.Iterables.getLast;

public class SyntaxParser {
    public static final String EQUAL_OPERATOR = "=";
    public static final String VECTOR_INDICATOR = "SEQUENCE OF";
    public static final String LINE_BREAK = "\n";

    public AsnClass parseClass(String content) {
        AsnClass rootClass = new AsnMainClass();
        Iterable<String> lines = Splitter.on(LINE_BREAK)
                .omitEmptyStrings()
                .split(content);

        for (String line : filterSpaceLines(lines)) {
            rootClass = generateAsnClassTree(rootClass, line);
        }
        return rootClass;
    }

    private Iterable<String> filterSpaceLines(Iterable<String> lines) {
        return Iterables.filter(lines, new Predicate<String>() {
            @Override
            public boolean apply(String line) {
                return !Strings.isNullOrEmpty(line.trim());
            }
        });
    }

    private AsnClass generateAsnClassTree(AsnClass currentClass, String line) {
        if (!isMainClass(line)) {
            if (endOfClass(line)) {
                currentClass = navigateToParent(currentClass);
            } else {
                if (isClassField(line) || isNestedClass(line)) {
                    currentClass = navigateToChild(currentClass, line);
                } else {
                    currentClass.addBasicField(parseBasicField(line));
                }
            }
        }
        return currentClass;
    }

    protected boolean isSequence(String line) {
        return line.contains("SEQUENCE") || line.contains("CHOICE");
    }

    protected boolean isMainClass(String line) {
        return line.startsWith("{");
    }

    protected BasicField parseBasicField(String line) {
        Iterable<String> fieldInfo = Splitter.on(EQUAL_OPERATOR)
                .trimResults(anyOf(" ,"))
                .limit(2)
                .split(line);

        return new BasicField(getFirst(fieldInfo, "ERROR_FIELD_NAME"), getLast(fieldInfo, ""));
    }

    protected AsnClass createAsnClass(String line) {
        if (isNestedClass(line)) {
            return new AsnSequenceClass();
        }
        String fieldName = getFieldName(line.split(EQUAL_OPERATOR));
        if (isVector(line)) {
            return new AsnVectorClass(fieldName);
        }
        return new AsnSequenceClass(fieldName);
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

    private AsnClass navigateToChild(AsnClass currentClass, String line) {
        AsnClass childProperty = createAsnClass(line);
        currentClass.addChildClass(childProperty);

        currentClass = childProperty;
        return currentClass;
    }

    private AsnClass navigateToParent(AsnClass currentClass) {
        AsnClass parentAsnClass = currentClass.getParentAsnClass();
        if (parentAsnClass != null) {
            currentClass = parentAsnClass;
        }
        return currentClass;
    }

    protected boolean isNestedClass(String line) {
        return line.length() != line.trim().length() && line.trim().startsWith("{");
    }

    protected boolean endOfClass(String line) {
        return line.trim().contains("}");
    }

    public List<BasicElement> parseBasicElements(String line) {
        List<String> elements = Splitter.on(",")
                .trimResults()
                .splitToList(line);
        return Lists.transform(elements, new Function<String, BasicElement>() {
            @Override
            public BasicElement apply(String element) {
                return new BasicElement(element);
            }
        });
    }

    public boolean isBasicElement(String line) {
        return CharMatcher.anyOf("{}=").matchesNoneOf(line);
    }
}
