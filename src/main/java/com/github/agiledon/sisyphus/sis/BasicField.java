package com.github.agiledon.sisyphus.sis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

import static com.github.agiledon.sisyphus.sis.util.BasicFields.getFieldValue;

public class BasicField {
    private final Logger logger = LoggerFactory.getLogger(BasicField.class);
    private String name;
    private String value;

    public BasicField(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public void setField(Object currentObject, Class<?> aClass) throws IllegalAccessException {
        try {
            Field field = aClass.getDeclaredField(name);
            field.set(currentObject, getFieldValue(field.getType(), value));
        } catch (NoSuchFieldException ex) {
            logger.warn("Can not find the field with {} and inner exception is {}", name, ex.getMessage());
        } catch (Exception ex) {
            logger.warn("Can not find the field with {} and inner exception is {}", name, ex.getMessage());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof BasicField) {
            BasicField that = (BasicField) o;
            if (name != null ? !name.equals(that.name) : that.name != null) {
                return false;
            }
            if (value != null ? !value.equals(that.value) : that.value != null) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
