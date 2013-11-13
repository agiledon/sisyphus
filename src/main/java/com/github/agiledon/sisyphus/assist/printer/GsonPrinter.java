package com.github.agiledon.sisyphus.assist.printer;

public class GsonPrinter extends AbstractPrinter {
    public <T> GsonPrinter(Class<T> tClass) {
        super(tClass);
    }

    @Override
    public void print(String fileName) {
    }
}
