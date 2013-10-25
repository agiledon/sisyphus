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
            result = deserialize(tClass, loadResourceAsLines(resourceName));
            results.put(resourceName, result);
        }
        return result;
    }

    protected abstract <T> T deserialize(Class<T> tClass, List<String> resource);

    protected String getContent(List<String> resource) {
        if (stringTemplate != null) {
            return stringTemplate.evaluate(resource);
        }
        return Joiner.on("\n").join(resource);
    }

    public void setTemplate(StringTemplate stringTemplate) {
        this.stringTemplate = stringTemplate;
    }

    @Override
    public void clearTemplate() {
        this.stringTemplate = null;
    }

    @Override
    public MultiSectionsComposer withTemplate(String templateFileName) {
        StringTemplate template = new StringTemplate(templateFileName);
        this.setTemplate(template);
        MultiSectionsComposer multiSectionsComposer = new MultiSectionsComposer(this);
        multiSectionsComposer.setResourceName(resourceName);
        return multiSectionsComposer;
    }
}
