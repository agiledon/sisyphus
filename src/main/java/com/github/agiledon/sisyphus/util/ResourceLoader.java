package com.github.agiledon.sisyphus.util;

import com.github.agiledon.sisyphus.exception.FailToReadDataFileException;
import com.google.common.base.Charsets;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.github.agiledon.sisyphus.util.ResourceFilePath.compensatePath;
import static com.github.agiledon.sisyphus.util.ResourceFilePath.getAbsolutePath;

public class ResourceLoader {
    public static List<String> loadResource(String resourceName) {
        try {
            File file = new File(getAbsolutePath(compensatePath(resourceName)));
            return Files.readLines(file, Charsets.UTF_8);
        } catch (IOException e) {
            throw new FailToReadDataFileException();
        }
    }
}
