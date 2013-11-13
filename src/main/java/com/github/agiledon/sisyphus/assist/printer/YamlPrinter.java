package com.github.agiledon.sisyphus.assist.printer;

import org.yaml.snakeyaml.Yaml;

public class YamlPrinter extends AbstractPrinter {
    @Override
    protected <T> String serialize(T sourceObject) {
        Yaml yaml = new Yaml();
        return yaml.dump(sourceObject);
    }
}
