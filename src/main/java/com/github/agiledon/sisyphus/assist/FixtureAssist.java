package com.github.agiledon.sisyphus.assist;

import com.github.agiledon.sisyphus.assist.printer.*;

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

    public static final ObjectFactory create() {
        return new ObjectFactory();
    }
}
