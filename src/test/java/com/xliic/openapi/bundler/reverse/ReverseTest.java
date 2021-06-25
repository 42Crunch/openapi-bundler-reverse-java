
package com.xliic.openapi.bundler.reverse;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReverseTest {
    @Test
    void testCRLF() throws IOException {
        File file = new File("src/test/resources/common-components-3-17.yaml").getCanonicalFile();
        String text = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
        Document document = Parser.parseYaml(text);
        long line = document.getLine("/components/schemas/Error/properties/message/type");
        assertEquals(42, line);
        line = document.getLine("/components/schemas/Pet/type");
        assertEquals(16, line);
    }

    @Test
    void testLF() throws IOException {
        File file = new File("src/test/resources/common-components-3-17-unix.yaml").getCanonicalFile();
        String text = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
        Document document = Parser.parseYaml(text);
        long line = document.getLine("/components/schemas/Error/properties/message/type");
        assertEquals(42, line);
        line = document.getLine("/components/schemas/Pet/type");
        assertEquals(16, line);
    }

    @Test
    void testJsonCRLF() throws IOException {
        File file = new File("src/test/resources/common-components-3-17.json").getCanonicalFile();
        String text = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
        Document document = Parser.parseJson(text);
        long line = document.getLine("/components/schemas/Error/properties/message/type");
        assertEquals(62, line);
        line = document.getLine("/components/schemas/Pet/type");
        assertEquals(26, line);
    }

    @Test
    void testJsonLF() throws IOException {
        File file = new File("src/test/resources/common-components-3-17-unix.json").getCanonicalFile();
        String text = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
        Document document = Parser.parseJson(text);
        long line = document.getLine("/components/schemas/Error/properties/message/type");
        assertEquals(62, line);
        line = document.getLine("/components/schemas/Pet/type");
        assertEquals(26, line);
    }
}
