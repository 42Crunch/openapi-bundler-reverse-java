package com.xliic.openapi.bundler.reverse;

import java.util.Map;

public class Document {
    private Map<String, Location> pointers;

    public Document(Map<String, Location> pointers) {
        this.pointers = pointers;
    }

    public long getLine(String jsonPointer) {
        return this.pointers.get(jsonPointer).getVisualLine();
    }
}