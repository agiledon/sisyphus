package com.github.agiledon.sisyphus.assist;

import com.github.agiledon.sisyphus.assist.printer.*;
import com.github.agiledon.sisyphus.sis.SisClass;
import com.github.agiledon.sisyphus.sis.SyntaxParser;

import java.lang.reflect.InvocationTargetException;

import static com.github.agiledon.sisyphus.sis.util.Reflection.createInstance;

public final class FixtureAssist {
    private FixtureAssist() {}

    public static final Printer json() {
        return new JsonPrinter();
    }

    public static final Printer gson() {
        return new GsonPrinter();
    }

    public static final Printer yaml() {
        return new YamlPrinter();
    }

    public static final Printer sis() {
        return new SisPrinter();
    }

    public static final <T> T stub(Class<T> sourceClass) {
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
