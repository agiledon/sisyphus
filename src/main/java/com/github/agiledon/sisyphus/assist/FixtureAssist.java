package com.github.agiledon.sisyphus.assist;

import com.github.agiledon.sisyphus.assist.printer.GsonPrinter;
import com.github.agiledon.sisyphus.assist.printer.JsonPrinter;
import com.github.agiledon.sisyphus.assist.printer.Printer;
import com.github.agiledon.sisyphus.assist.printer.YamlPrinter;

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
}
