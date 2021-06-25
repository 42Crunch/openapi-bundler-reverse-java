package com.xliic.openapi.bundler.reverse;

import com.xliic.openapi.parser.ast.node.Node;

public class Document {

    private final Node root;

    public Document(Node root) {
        this.root = root;
    }

    public long getLine(String jsonPointer) {
        return root.find(jsonPointer).getRange().getLine() + 1;
    }
}