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
            result = evaluate(tClass);
            results.put(resourceName, result);
        }
        return result;
    }

    protected  <T> T evaluate(Class<T> tClass) {
        return deserialize(tClass, getContent(loadResourceAsLines(resourceName)));
    }

    protected abstract <T> T deserialize(Class<T> tClass, String content);

    protected String getContent(List<String> resource) {
        return Joiner.on("\n").join(resource);
    }

    @Override
    public MultiSectionsComposer withTemplate(String templateFileName) {
        StringTemplateComposer stringTemplateComposer = new StringTemplateComposer(this, templateFileName);
        stringTemplateComposer.setResourceName(resourceName);
        MultiSectionsComposer multiSectionsComposer = new MultiSectionsComposer(stringTemplateComposer);
        multiSectionsComposer.setResourceName(resourceName);

        return multiSectionsComposer;
    }
}
