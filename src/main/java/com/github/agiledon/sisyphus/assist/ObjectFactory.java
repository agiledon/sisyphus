package com.github.agiledon.sisyphus.assist;

import com.github.agiledon.sisyphus.sis.SisClass;
import com.github.agiledon.sisyphus.sis.SyntaxParser;

import java.lang.reflect.InvocationTargetException;

import static com.github.agiledon.sisyphus.sis.util.Reflection.createInstance;

public class ObjectFactory {
    public <T> T with(Class<T> sourceClass) {
        try {
            T instance = createInstance(sourceClass);
            SisClass sisClass = new SyntaxParser(false).parseClassFromObject(instance);
            return sisClass.instantiate(sourceClass);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
