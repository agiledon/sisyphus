package com.github.agiledon.sisyphus.composer;

import com.github.agiledon.sisyphus.util.StringTemplate;
import java.util.List;

public class StringTemplateComposer extends ComposerDecorator {
    private StringTemplate stringTemplate;

    public StringTemplateComposer(AbstractComposer composer, String templateFileName) {
        super(composer);
        stringTemplate = new StringTemplate(templateFileName);
    }

    @Override
    protected String evaluate(List<String> resource) {
        if (stringTemplate != null) {
            return stringTemplate.evaluate(resource);
        }
        return decoratedComposer.evaluate(resource);
    }
}
