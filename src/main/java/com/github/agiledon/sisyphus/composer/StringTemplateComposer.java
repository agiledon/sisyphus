package com.github.agiledon.sisyphus.composer;

import com.github.agiledon.sisyphus.composer.template.StringTemplate;
import com.google.common.base.Joiner;

import java.util.List;

public class StringTemplateComposer extends ComposerDecorator {
    private StringTemplate stringTemplate;

    public StringTemplateComposer(AbstractComposer composer, String templateFileName) {
        super(composer);
        stringTemplate = new StringTemplate(templateFileName);
    }

    @Override
    protected String getContent(List<String> resource) {
        if (stringTemplate != null) {
            return stringTemplate.evaluate(resource);
        }
        return Joiner.on("\n").join(resource);
    }
}
