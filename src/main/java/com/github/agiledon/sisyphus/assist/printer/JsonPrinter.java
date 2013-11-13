package com.github.agiledon.sisyphus.assist.printer;

@SuppressWarnings("unchecked")
public class JsonPrinter<T> extends AbstractPrinter<T> {
    public JsonPrinter(T sourceObject) {
        super(sourceObject);
    }

    @Override
    protected String serialize(T sourceObject) {
        return null;
    }
}
