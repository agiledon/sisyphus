package com.github.agiledon.sisyphus.assist;

import com.github.agiledon.sisyphus.assist.printer.*;

@SuppressWarnings("unchecked")
public final class FixtureAssist {
    private FixtureAssist() {}

    public static final <T> Printer json(T sourceObject) {
        return new JsonPrinter(sourceObject);
    }

    public static final <T> Printer gson(T sourceObject) {
        return new GsonPrinter(sourceObject);
    }

    public static final <T> Printer yaml(T sourceObject) {
        return new YamlPrinter(sourceObject);
    }

    public static final <T> Printer sis(T sourceObject) {
        return new SisPrinter(sourceObject);
    }
}
