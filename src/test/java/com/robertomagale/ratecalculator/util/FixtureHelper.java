package com.robertomagale.ratecalculator.util;

import static java.util.Objects.requireNonNull;

public class FixtureHelper {

    protected String getResourcePath(final String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        return requireNonNull(classLoader.getResource(fileName)).getFile();
    }

}
