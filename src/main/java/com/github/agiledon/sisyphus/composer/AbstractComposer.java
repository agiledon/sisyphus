package com.github.agiledon.sisyphus.composer;

import com.google.common.base.Joiner;

import java.util.List;
import java.util.Map;

import static com.github.agiledon.sisyphus.util.ResourceLoader.loadResourceAsLines;
import static com.google.common.collect.Maps.newHashMap;

public abstract class AbstractComposer implements Composer {
    protected String resourceName;
    private Map<String, Object> results = newHashMap();

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    @SuppressWarnings("unchecked")
    public <T> T to(Class<T> tClass) {
        T result = (T)results.get(resourceName);
        if (result == null) {
            String content = evaluate(loadResourceAsLines(resourceName));
            result = deserialize(tClass, content);
            results.put(resourceName, result);
        }
        return result;
    }

    protected String evaluate(List<String> resource) {
        return Joiner.on("\n").join(resource);
    }

    protected abstract <T> T deserialize(Class<T> tClass, String content);

    @Override
    public MultiSectionsComposer withTemplate(String templateFileName) {
        return new MultiSectionsComposer(new StringTemplateComposer(this, templateFileName));
    }
}
