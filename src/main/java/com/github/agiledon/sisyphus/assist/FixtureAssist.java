package com.github.agiledon.sisyphus.assist;

import com.github.agiledon.sisyphus.assist.printer.*;

public final class FixtureAssist {
    public static final <T> Printer json(Class<T> tClass) {
        return new JsonPrinter(tClass);
    }

    public static final <T> Printer gson(Class<T> tClass) {
        return new GsonPrinter(tClass);
    }

    public static final <T> Printer yaml(Class<T> tClass) {
        return new YamlPrinter(tClass);
    }

    public static final <T> Printer sis(Class<T> tClass) {
        return new SisPrinter(tClass);
    }
}
