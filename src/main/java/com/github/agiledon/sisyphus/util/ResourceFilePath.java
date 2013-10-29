package com.github.agiledon.sisyphus.util;

public abstract class ResourceFilePath {
    public static String compensatePath(String resourceName) {
        if (!resourceName.startsWith("/")) {
            resourceName = "/" + resourceName;
        }
        return resourceName;
    }

    public static String getAbsolutePath(String resourceName) {
        return ResourceFilePath.class.getResource(compensatePath(resourceName)).getPath();
    }
}
