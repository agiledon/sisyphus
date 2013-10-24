package com.github.agiledon.sisyphus.composer;

public interface Composer {
    <T> T to(Class<T> tClass);
    Composer withTemplate(String templateFileName);
    void setResourceName(String resourceName);
    void clearTemplate();
}
