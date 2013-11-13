package com.github.agiledon.sisyphus.assist.printer;

public abstract class AbstractPrinter<T> implements Printer {
    private  Class<T> sourceClass;

    protected AbstractPrinter(Class<T> sourceClass) {
        this.sourceClass = sourceClass;
    }

    @Override
    public Class<T> sourceClass() {
        return sourceClass;
    }
}
