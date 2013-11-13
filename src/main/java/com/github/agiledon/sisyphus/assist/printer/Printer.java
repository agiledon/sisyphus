package com.github.agiledon.sisyphus.assist.printer;

public interface Printer {
    <T> void print(T sourceObject, String fileName);
}
