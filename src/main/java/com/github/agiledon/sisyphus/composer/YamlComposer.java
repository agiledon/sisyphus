package com.github.agiledon.sisyphus.composer;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.introspector.BeanAccess;

import java.util.List;

public class YamlComposer extends AbstractComposer {
    @Override
    public <T> T deserialize(Class<T> tClass, List<String> resource) {
        Yaml yaml = new Yaml();
        yaml.setBeanAccess(BeanAccess.FIELD);
        return yaml.loadAs(getContent(resource), tClass);
    }
}
