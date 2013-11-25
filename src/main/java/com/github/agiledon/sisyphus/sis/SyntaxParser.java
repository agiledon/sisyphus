package com.github.agiledon.sisyphus.sis;

import com.github.agiledon.sisyphus.exception.FailedDeserializationException;
import com.github.agiledon.sisyphus.exception.FailedSerializationException;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static com.github.agiledon.sisyphus.sis.rule.ParsingRule.sisClassTree;
import static com.github.agiledon.sisyphus.sis.rule.ParsingRule.createRootClass;
import static com.github.agiledon.sisyphus.sis.util.Reflection.createInstance;
import static com.github.agiledon.sisyphus.sis.util.Reflection.getFieldValue;
import static com.github.agiledon.sisyphus.sis.util.Reflection.isPrimitiveType;
import static com.google.common.base.Preconditions.checkArgument;

public class SyntaxParser {
    private final Logger logger = LoggerFactory.getLogger(SyntaxParser.class);
    private static final String LINE_BREAK = "\n";
    private boolean ignoreNullField = true;

    public SyntaxParser() {
    }

    public SyntaxParser(boolean ignoreNullField) {
        this.ignoreNullField = ignoreNullField;
    }

    public SisClass parseClassFromResource(String resource) {
        SisClass rootClass;

        try {
            List<String> lines = splitLines(resource);
            checkArgument(lines != null && lines.size() >= 1, "data file is error");
            rootClass = createRootClass(lines.get(0));
            for (int i = 1; i < lines.size(); i++) {
                rootClass = sisClassTree(rootClass, lines.get(i));
            }
            return rootClass;
        } catch (IllegalArgumentException ex) {
            return logAndRethrowException(ex);
        } catch (Exception ex) {
            return logAndRethrowException(ex);
        }
    }

    public <T> SisClass parseClassFromObject(T sourceObject) {
        return parseClass(sourceObject, null, 0);
    }

    private <T> SisClass parseClass(T sourceObject, String fieldName, int level) {
        if (sourceObject == null) {
            throw new FailedSerializationException("The target is null");
        }
        if (isArray(sourceObject)) {
            return createArrayClass(sourceObject, fieldName, level);
        }
        if (isList(sourceObject)) {
            return createListClass(sourceObject, fieldName, level);
        }

        return createNormalClass(sourceObject, fieldName, level);
    }

    private <T> SisClass createArrayClass(T sourceObject, String fieldName, int level) {
        SisArrayClass sisArrayClass = new SisArrayClass(fieldName);
        sisArrayClass.setCurrentLevel(level);

        Object[] array = (Object[]) sourceObject;
        if (ignoreNullField && (array == null || array.length == 0)) {
            return sisArrayClass;
        }

        Object firstElement = array[0];
        if (isPrimitiveType(firstElement.getClass())) {
            for (Object element : array) {
                sisArrayClass.addBasicElement(new BasicElement(element.toString()));
            }
        } else {
            for (Object element : array) {
                sisArrayClass.addChildClass(parseClass(element, null, level + 1));
            }
        }

        return sisArrayClass;
    }

    private <T> SisClass createListClass(T sourceObject, String fieldName, int level) {
        SisListClass sisListClass = new SisListClass(fieldName);
        sisListClass.setCurrentLevel(level);

        ArrayList list = (ArrayList) sourceObject;
        if (list == null || list.size() == 0) {
            return sisListClass;
        }

        Object firstElement = list.get(0);
        if (isPrimitiveType(firstElement.getClass())) {
            for (Object element : list) {
                sisListClass.addBasicElement(new BasicElement(element.toString()));
            }
        } else {
            for (Object element : list) {
                sisListClass.addChildClass(parseClass(element, null, level + 1));
            }
        }

        return sisListClass;
    }

    private <T> SisNormalClass createNormalClass(T sourceObject, String fieldName, int level) {
        SisNormalClass sisNormalClass = new SisNormalClass(fieldName);
        sisNormalClass.setCurrentLevel(level);

        Field[] declaredFields = sourceObject.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            setOrIgnoreFieldValue(sourceObject, sisNormalClass, declaredField, level);
        }
        return sisNormalClass;
    }

    private <T> void setOrIgnoreFieldValue(T sourceObject, SisNormalClass sisNormalClass, Field declaredField, int level) {
        try {
            declaredField.setAccessible(true);
            Object fieldValue = declaredField.get(sourceObject);
            if (fieldValue == null && ignoreNullField) {
                return;
            }
            if (isPrimitiveType(declaredField.getType())) {
                if (fieldValue == null) {
                    fieldValue = getFieldValue(declaredField.getType(), "");
                }
                sisNormalClass.addBasicField(new BasicField(declaredField.getName(), fieldValue.toString()));
            } else {
                if (fieldValue == null) {
                    fieldValue = createInstance(declaredField.getType());
                }
                sisNormalClass.addChildClass(parseClass(fieldValue, declaredField.getName(), level + 1));
            }
        } catch (IllegalAccessException e) {
            logAndRethrowException(e);
        } catch (InstantiationException e) {
            logAndRethrowException(e);
        } catch (InvocationTargetException e) {
            logAndRethrowException(e);
        } catch (Exception e) {
            logAndRethrowException(e);
        }
    }

    private <T> boolean isList(T sourceObject) {
        Class<?> sourceClass = sourceObject.getClass();
        return sourceClass.getSuperclass() != null &&
                (sourceClass.getSuperclass().getSimpleName().equals("ArrayList") ||
                        sourceClass.getSuperclass().getSimpleName().equals("List") ||
                        sourceClass.getSimpleName().equals("ArrayList"));
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
