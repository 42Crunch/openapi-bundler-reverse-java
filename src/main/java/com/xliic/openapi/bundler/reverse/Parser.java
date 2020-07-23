package com.xliic.openapi.bundler.reverse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Parser {

    private static PointerToLocationJSONParser pointerToLocationJSONParser = new PointerToLocationJSONParser();
    private static PointerToLocationYAMLParser pointerToLocationYAMLParser = new PointerToLocationYAMLParser();

    public static Document parseJson(String text) {
        return new Document(pointerToLocationJSONParser.parse(text));

    }

    public static Document parseYaml(String text) {
        return new Document(pointerToLocationYAMLParser.parse(text));
    }

    static String readFile(String filename) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filename)), StandardCharsets.UTF_8);
    }
}
