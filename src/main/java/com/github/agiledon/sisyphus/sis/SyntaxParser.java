package com.github.agiledon.sisyphus.sis;

import com.github.agiledon.sisyphus.exception.FailedDeserializationException;
import com.github.agiledon.sisyphus.exception.FailedSerializationException;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.github.agiledon.sisyphus.sis.rule.ParsingRule.sisClassTree;
import static com.github.agiledon.sisyphus.sis.rule.ParsingRule.createRootClass;
import static com.github.agiledon.sisyphus.sis.util.BasicFields.isPrimitiveType;
import static com.google.common.base.Preconditions.checkArgument;

public class SyntaxParser {
    private final Logger logger = LoggerFactory.getLogger(SyntaxParser.class);
    private static final String LINE_BREAK = "\n";

    public SisClass parseClassFromResource(String resource) {
        SisClass rootClass;

        try {
            List<String> lines = splitLines(resource);
            checkArgument(lines != null && lines.size() >= 1, "data file is error");
            rootClass = createRootClass(lines.get(0));
            for (String line : lines) {
                rootClass = sisClassTree(rootClass, line);
            }
            return rootClass;
        } catch (IllegalArgumentException ex) {
            return logAndRethrowException(ex);
        } catch (Exception ex) {
            return logAndRethrowException(ex);
        }
    }

    public <T> SisClass parseClassFromObject(T sourceObject) {
        return parseClass(sourceObject, null);
    }

    private <T> SisClass parseClass(T sourceObject, String fieldName) {
        if (sourceObject == null) {
            throw new FailedSerializationException("The target is null");
        }
        if (isArray(sourceObject)) {
            return new SisCollectionClass();
        }
        if (isList(sourceObject)) {
            return createListClass(sourceObject, fieldName);
        }

        return createNormalClass(sourceObject, fieldName);
    }

    private <T> SisClass createListClass(T sourceObject, String fieldName) {
        SisListClass sisListClass = new SisListClass(fieldName);

        ArrayList list = (ArrayList) sourceObject;
        if (list == null || list.size() == 0) {
            return sisListClass;
        }

        Object firstElement = list.get(0);
        if (isPrimitiveType(firstElement.getClass().getSimpleName())) {
            for (Object element : list) {
                sisListClass.addBasicElement(new BasicElement(element.toString()));
            }
        } else {
            for (Object element : list) {
                sisListClass.addChildClass(parseClass(element, null));
            }
        }

        return sisListClass;
    }

    private <T> SisNormalClass createNormalClass(T sourceObject, String fieldName) {
        SisNormalClass resultClass = new SisNormalClass(fieldName);

        Field[] declaredFields = sourceObject.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            try {
                Object fieldValue = declaredField.get(sourceObject);
                if (fieldValue == null) {
                    continue;
                }
                if (isPrimitiveType(fieldValue.getClass().getSimpleName())) {
                    resultClass.addBasicField(new BasicField(declaredField.getName(), fieldValue.toString()));
                } else {
                    resultClass.addChildClass(parseClass(fieldValue, declaredField.getName()));
                }
            } catch (IllegalAccessException e) {
                continue;
            }
        }
        return resultClass;
    }

    private <T> boolean isList(T sourceObject) {
        Class<?> sourceClass = sourceObject.getClass();
        return sourceClass.getSuperclass().getSimpleName().equals("ArrayList") ||
               sourceClass.getSuperclass().getSimpleName().equals("List") ||
               sourceClass.getSimpleName().equals("ArrayList");
    }

    private <T> boolean isArray(T sourceObject) {
        return sourceObject.getClass().getSimpleName().endsWith("[]");
    }

    private SisClass logAndRethrowException(Exception ex) {
        logger.error(ex.getMessage());
        throw new FailedDeserializationException(ex);
    }

    private List<String> splitLines(String content) {
        Iterable<String> lines = Splitter.on(LINE_BREAK)
                .omitEmptyStrings()
                .split(content);

        return Lists.newArrayList(lines);
    }
}
