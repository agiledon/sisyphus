package com.github.agiledon.sisyphus.assist.printer;

public class SisPrinter extends AbstractPrinter {
    @Override
    protected String getExtension() {
        return "sis";
    }

    @Override
    protected <T> String serialize(T sourceObject) {
        return null;
    }
}
