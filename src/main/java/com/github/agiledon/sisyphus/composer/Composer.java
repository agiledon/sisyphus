package com.github.agiledon.sisyphus.composer;

public interface Composer {
    <T> T to(Class<T> tClass);
    MultiSectionsComposer withTemplate(String templateFileName);
    void setResourceName(String resourceName);
}
