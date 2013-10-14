package com.github.agiledon.sisyphus.util;

import com.github.agiledon.sisyphus.exception.FailToReadResourceException;
import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.github.agiledon.sisyphus.util.ResourceFilePath.compensatePath;
import static com.github.agiledon.sisyphus.util.ResourceFilePath.getAbsolutePath;

public class ResourceLoader {
    public static String loadResource(String resourceName) {
        try {
            File file = new File(getAbsolutePath(compensatePath(resourceName)));
            List<String> contents = Files.readLines(file, Charsets.UTF_8);
            return Joiner.on("\n").join(contents);
        } catch (IOException e) {
            throw new FailToReadResourceException();
        }
    }
}
