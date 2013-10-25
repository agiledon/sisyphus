package com.github.agiledon.sisyphus.composer;

import com.github.agiledon.sisyphus.composer.template.StringTemplate;
import com.google.common.base.Joiner;

import java.util.List;
import java.util.Map;

import static com.github.agiledon.sisyphus.util.ResourceLoader.loadResourceAsLines;
import static com.google.common.collect.Maps.newHashMap;

public abstract class AbstractComposer implements Composer {
    protected String resourceName;
    private Map<String, Object> results = newHashMap();
    private StringTemplate stringTemplate;

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    @SuppressWarnings("unchecked")
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
        List<String> content = loadResourceAsLines(resourceName);
        if (stringTemplate != null) {
            return stringTemplate.evaluate(content);
        }
        return Joiner.on("\n").join(content);
    }

    public void setTemplate(StringTemplate stringTemplate) {
        this.stringTemplate = stringTemplate;
    }

    @Override
    public void clearTemplate() {
        this.stringTemplate = null;
    }

    @Override
    public Composer withTemplate(String templateFileName) {
        this.setTemplate(new StringTemplate(templateFileName));
        return this;
    }
}
