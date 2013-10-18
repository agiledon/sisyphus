package com.github.agiledon.sisyphus.composer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnknownComposer extends AbstractComposer {
    private final Logger logger = LoggerFactory.getLogger(UnknownComposer.class);
    @Override
    protected <T> T deserialize(Class<T> tClass) {
        logger.info("Unknown file type, can't compose the java object and return null");
        return null;
    }
}
