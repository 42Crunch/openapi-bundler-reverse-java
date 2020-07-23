package com.xliic.openapi.bundler.reverse;

public class Location {

    private final long line;
    private final long column;
    private final long startOffset;
    private final long endOffset;

    public Location() {
        line = 0;
        column = 0;
        endOffset = 0;
        startOffset = 0;
    }

    public Location(long line, long column, long startOffset, long endOffset) {
        this.line = line;
        this.column = column;
        this.startOffset = startOffset;
        this.endOffset = endOffset;
    }

    public long getLine() {
        return line;
    }

    public long getVisualLine() {
        return line + 1;
    }

    public long getColumn() {
        return column;
    }

    public long getVisualColumn() {
        return column + 1;
    }

    public long getStartOffset() {
        return startOffset;
    }

    public long getEndOffset() {
        return endOffset;
    }

}
