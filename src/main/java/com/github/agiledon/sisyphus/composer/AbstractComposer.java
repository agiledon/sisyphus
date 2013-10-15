package com.github.agiledon.sisyphus.composer;

import com.github.agiledon.sisyphus.composer.parser.StringTemplateParser;
import com.google.common.base.Joiner;

import java.util.List;
import java.util.Map;

import static com.github.agiledon.sisyphus.util.ResourceLoader.loadTextLines;
import static com.google.common.collect.Maps.newHashMap;

public abstract class AbstractComposer implements Composer {
    protected String resourceName;
    private Map<String, Object> results = newHashMap();
    private StringTemplateParser templateParser;

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
        List<String> content = loadTextLines(resourceName);
        if (templateParser != null) {
            return templateParser.evaluate(content);
        }
        return Joiner.on("\n").join(content);
    }

    public void setTemplateParser(StringTemplateParser templateParser) {
        this.templateParser = templateParser;
    }

    @Override
    public void clearTemplateParser() {
        this.templateParser = null;
    }

    @Override
    public Composer withTemplate(String templateFileName) {
        this.setTemplateParser(new StringTemplateParser(templateFileName));
        return this;
    }
}
