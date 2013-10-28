package com.github.agiledon.sisyphus.composer;

public abstract class ComposerDecorator extends AbstractComposer {
    protected AbstractComposer composer;

    public ComposerDecorator(AbstractComposer composer) {
        this.composer = composer;
    }

    @Override
    protected <T> T deserialize(Class<T> tClass, String content) {
        return composer.deserialize(tClass, content);
    }
}
