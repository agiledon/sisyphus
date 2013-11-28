package com.github.agiledon.sisyphus.sis;

import com.github.agiledon.sisyphus.sis.util.Reflection;

import java.util.ArrayList;

public class ListObjectParser extends ObjectParser {
    public ListObjectParser(boolean ignoreNullField) {
        super(ignoreNullField);
    }

    @Override
    public <T> SisClass parseClass(T sourceObject, String fieldName, int level) {
        return createListClass(sourceObject, fieldName, level);
    }

    private <T> SisClass createListClass(T sourceObject, String fieldName, int level) {
        SisListClass sisListClass = new SisListClass(fieldName);
        sisListClass.setCurrentLevel(level);

        ArrayList list = (ArrayList) sourceObject;
        if (list == null || list.size() == 0) {
            return sisListClass;
        }

        Object firstElement = list.get(0);
        if (Reflection.isPrimitiveType(firstElement.getClass())) {
            for (Object element : list) {
                sisListClass.addBasicElement(new BasicElement(element.toString()));
            }
        } else {
            for (Object element : list) {
                sisListClass.addChildClass(parser(element, isIgnoreNullField()).parseClass(element, null, level + 1));
            }
        }

        return sisListClass;
    }
}
