package com.github.agiledon.sisyphus.assist.printer;

@SuppressWarnings("unchecked")
public class GsonPrinter<T> extends AbstractPrinter<T> {
    public GsonPrinter(T sourceObject) {
        super(sourceObject);
    }

    @Override
    protected String serialize(T sourceObject) {
        return null;
    }
}
