package com.github.agiledon.sisyphus.assist.printer;

public interface Printer {
    void print(String fileName);
    <T> Class<T> sourceClass();
}
