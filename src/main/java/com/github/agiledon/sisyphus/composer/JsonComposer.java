package com.github.agiledon.sisyphus.composer;

import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class JsonComposer extends AbstractComposer {

    @Override
    protected <T> T deserialize(Class<T> tClass) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(getContent(), tClass);
        } catch (JsonMappingException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }
}
