package com.github.agiledon.sisyphus.assist.printer;

@SuppressWarnings("unchecked")
public class YamlPrinter<T> extends AbstractPrinter<T> {
    public YamlPrinter(T sourceObject) {
        super(sourceObject);
    }

    @Override
    protected String serialize(T sourceObject) {
        return null;
    }
}
