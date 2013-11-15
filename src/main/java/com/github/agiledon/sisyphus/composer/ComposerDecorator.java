package com.github.agiledon.sisyphus.composer;

import java.util.List;

public abstract class ComposerDecorator extends AbstractComposer {
    protected AbstractComposer decoratedComposer;

    public ComposerDecorator(AbstractComposer decoratedComposer) {
        this.decoratedComposer = decoratedComposer;
        this.resourceName = decoratedComposer.resourceName;
    }

    @Override
    protected <T> T deserialize(Class<T> tClass, String resource) {
        return decoratedComposer.deserialize(tClass, resource);
    }

    @Override
    protected String evaluate(List<String> resource) {
        return decoratedComposer.evaluate(resource);
    }
}
