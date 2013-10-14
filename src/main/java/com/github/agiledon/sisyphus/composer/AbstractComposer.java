package com.github.agiledon.sisyphus.composer;

import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

public abstract class AbstractComposer implements Composer {
    protected String resourceName;
    private Map<String, Object> results = newHashMap();

    @Override
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    @Override
    public <T> T to(Class<T> tClass) {
        T result = (T)results.get(resourceName);
        if (result == null) {
            result = deserialize(tClass);
            results.put(resourceName, result);
        }
        return result;
    }

    protected abstract <T> T deserialize(Class<T> tClass);
}
