package com.github.agiledon.sisyphus.assist.printer;

@SuppressWarnings("unchecked")
public class SisPrinter<T> extends AbstractPrinter<T> {
    public SisPrinter(T sourceObject) {
        super(sourceObject);
    }

    @Override
    protected String serialize(T sourceObject) {
        return null;
    }
}
