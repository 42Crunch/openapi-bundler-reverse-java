package com.xliic.openapi.bundler.reverse;

import com.xliic.openapi.parser.ast.ParserJsonAST;
import com.xliic.openapi.parser.ast.ParserYamlAST;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Parser {

    private static final ParserJsonAST parserJsonAST = new ParserJsonAST();
    private static final ParserYamlAST parserYamlAST = new ParserYamlAST();

    public static Document parseJson(String text) {
        return new Document(parserJsonAST.parse(text));
    }

    public static Document parseYaml(String text) {
        return new Document(parserYamlAST.parse(text));
    }

    static String readFile(String filename) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filename)), StandardCharsets.UTF_8);
    }
}
