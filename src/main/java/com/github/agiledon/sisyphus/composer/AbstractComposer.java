package com.github.agiledon.sisyphus.composer;

import com.google.common.base.Joiner;

import java.util.List;
import java.util.Map;

import static com.github.agiledon.sisyphus.util.ResourceLoader.loadResource;
import static com.google.common.collect.Maps.newHashMap;

public abstract class AbstractComposer implements Composer {
    protected String resourceName;
    private Map<String, Object> results = newHashMap();

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public <T> T to(Class<T> tClass) {
        T result = (T)results.get(resourceName);
        if (result == null) {
            result = deserialize(tClass);
            results.put(resourceName, result);
        }
        return result;
    }

    protected abstract <T> T deserialize(Class<T> tClass);

    protected String getContent() {
        List<String> resource = loadResource(resourceName);
        return Joiner.on("\n").join(resource);
    }
}
