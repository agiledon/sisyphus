package com.github.agiledon.sisyphus.sis;

import com.github.agiledon.sisyphus.exception.FailedDeserializationException;
import com.github.agiledon.sisyphus.exception.FailedSerializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ObjectParser {
    private boolean ignoreNullField = true;
    protected final Logger logger = LoggerFactory.getLogger(ObjectParser.class);

    public ObjectParser(boolean ignoreNullField) {
        this.ignoreNullField = ignoreNullField;
    }

    public static <T> ObjectParser parser(T sourceObject, boolean ignoreNullField) {
        if (sourceObject == null) {
            throw new FailedSerializationException("The target is null");
        }
        if (isArray(sourceObject)) {
            return new ArrayObjectParser(ignoreNullField);
        }
        if (isList(sourceObject)) {
            return new ListObjectParser(ignoreNullField);
        }

        return new NormalObjectParser(ignoreNullField);
    }

    public <T> SisClass parseClass(T sourceObject, String fieldName, int level) {
        return createSisClass(sourceObject, fieldName, level);
    }

    protected abstract <T> SisClass createSisClass(T sourceObject, String fieldName, int level);

    protected SisClass logAndRethrowException(Exception ex) {
        logger.error(ex.getMessage());
        throw new FailedDeserializationException(ex);
    }

    public boolean isIgnoreNullField() {
        return ignoreNullField;
    }

    private static <T> boolean isList(T sourceObject) {
        Class<?> sourceClass = sourceObject.getClass();
        return sourceClass.getSuperclass() != null &&
                (sourceClass.getSuperclass().getSimpleName().equals("ArrayList") ||
                        sourceClass.getSuperclass().getSimpleName().equals("List") ||
                        sourceClass.getSimpleName().equals("ArrayList"));
    }

    private static <T> boolean isArray(T sourceObject) {
        return sourceObject.getClass().isArray();
    }
}