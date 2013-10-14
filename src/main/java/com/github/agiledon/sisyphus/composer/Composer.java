package com.github.agiledon.sisyphus.composer;

public interface Composer {
    <T> T to(Class<T> tClass);
    void setResourceName(String resourceName);
}
