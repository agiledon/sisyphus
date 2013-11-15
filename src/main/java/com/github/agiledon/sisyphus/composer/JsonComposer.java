package com.github.agiledon.sisyphus.composer;

import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JsonComposer extends AbstractComposer {
    private final Logger logger = LoggerFactory.getLogger(JsonComposer.class);

    @Override
    protected <T> T deserialize(Class<T> tClass, String resource) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(resource, tClass);
        } catch (JsonMappingException e) {
            logger.error("Faild to de-serialize json content with {}. Return null Object", e.getMessage());
            return null;
        } catch (IOException e) {
            logger.error("Failed to read file {}. Return null object", resourceName);
            return null;
        }
    }
}
