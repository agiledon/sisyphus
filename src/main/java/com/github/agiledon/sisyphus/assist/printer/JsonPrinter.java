package com.github.agiledon.sisyphus.assist.printer;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class JsonPrinter extends AbstractPrinter {
    @Override
    protected <T> String serialize(T sourceObject) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(sourceObject);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return "";
        }
    }
}
