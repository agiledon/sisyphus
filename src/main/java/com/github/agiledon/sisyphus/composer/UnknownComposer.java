package com.github.agiledon.sisyphus.composer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UnknownComposer extends AbstractComposer {
    private final Logger logger = LoggerFactory.getLogger(UnknownComposer.class);
    @Override
    protected <T> T deserialize(Class<T> tClass, List<String> resource) {
        logger.info("Unknown file type, can't compose the java object and return null");
        return null;
    }
}
