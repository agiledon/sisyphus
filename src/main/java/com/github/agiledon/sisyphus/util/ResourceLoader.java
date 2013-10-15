package com.github.agiledon.sisyphus.util;

import com.github.agiledon.sisyphus.exception.FailToReadTextFileException;
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
        List<String> resource = loadTextLines(resourceName);
        return Joiner.on("\n").join(resource);
    }

    public static List<String> loadTextLines(String textFileName) {
        File file = new File(getAbsolutePath(compensatePath(textFileName)));
        try {
            return Files.readLines(file, Charsets.UTF_8);
        } catch (IOException e) {
            throw new FailToReadTextFileException();
        }
    }


}
