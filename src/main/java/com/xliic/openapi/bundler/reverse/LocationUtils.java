package com.xliic.openapi.bundler.reverse;

import org.json.JSONLocation;
import org.json.LocationHolder;
import org.snakeyaml.engine.v2.exceptions.Mark;
import org.snakeyaml.engine.v2.nodes.MappingNode;
import org.snakeyaml.engine.v2.nodes.NodeTuple;
import org.snakeyaml.engine.v2.nodes.ScalarNode;
import org.snakeyaml.engine.v2.nodes.SequenceNode;

import java.util.Optional;

public class LocationUtils {

    public final static String EMPTY_POINTER = "";
    public final static String POINTER_SEPARATOR = "/";

    public static String pointer(String key) {
        return pointer(EMPTY_POINTER, key);
    }

    public static String pointer(String parentPointer, String key) {
        return parentPointer + POINTER_SEPARATOR + LocationUtils.escape(key);
    }

    public static String pointer(String parentPointer, int index) {
        return parentPointer + POINTER_SEPARATOR + index;
    }

    public static String escape(String token) {
        return token.replace("~", "~0").replace("/", "~1").replace("\\", "\\\\").replace("\"", "\\\"");
    }

    public static Location getLocationForJSONEntry(Object entry) {
        JSONLocation location = ((LocationHolder) entry).getLocation();
        if (location == null) {
            return new Location();
        }
        return new Location(location.getLine(), location.getColumn(), location.getStartOffset(),
                location.getEndOffset());
    }

    public static Location getLocationForYAMLEntry(Object entry) {

        Optional<Mark> startMark, endMark;
        if (entry instanceof NodeTuple) {
            startMark = ((NodeTuple) entry).getKeyNode().getStartMark();
            endMark = ((NodeTuple) entry).getKeyNode().getEndMark();
            if (startMark.isPresent() && endMark.isPresent()) {
                int line = startMark.get().getLine();
                int column = startMark.get().getColumn();
                int startOffset = startMark.get().getIndex();
                int endOffset = endMark.get().getIndex();
                return new Location(line, column, startOffset, endOffset);
            }
        } else if (entry instanceof MappingNode) {
            if (((MappingNode) entry).getValue().isEmpty()) {
                startMark = ((MappingNode) entry).getStartMark();
                if (startMark.isPresent()) {
                    int line = startMark.get().getLine();
                    int column = startMark.get().getColumn();
                    int startOffset = startMark.get().getIndex();
                    endMark = ((MappingNode) entry).getEndMark();
                    if (endMark.isPresent()) {
                        int endOffset = endMark.get().getIndex();
                        return new Location(line, column, startOffset, endOffset);
                    }
                }
            } else {
                NodeTuple tuple = ((MappingNode) entry).getValue().get(0);
                startMark = tuple.getKeyNode().getStartMark();
                if (startMark.isPresent()) {
                    int line = startMark.get().getLine();
                    int column = startMark.get().getColumn();
                    int startOffset = startMark.get().getIndex();
                    int endOffset;
                    if (tuple.getValueNode() instanceof ScalarNode) {
                        endMark = tuple.getValueNode().getEndMark();
                    } else {
                        endMark = tuple.getKeyNode().getEndMark();
                    }
                    if (endMark.isPresent()) {
                        endOffset = endMark.get().getIndex();
                        return new Location(line, column, startOffset, endOffset);
                    }
                }
            }
            return new Location();
        } else if (entry instanceof SequenceNode) {
            startMark = ((SequenceNode) entry).getStartMark();
            if (((SequenceNode) entry).getValue().isEmpty()) {
                endMark = ((SequenceNode) entry).getEndMark();
                if (startMark.isPresent() && endMark.isPresent()) {
                    int line = startMark.get().getLine();
                    int column = startMark.get().getColumn();
                    int startOffset = startMark.get().getIndex();
                    int endOffset = endMark.get().getIndex();
                    return new Location(line, column, startOffset, endOffset);
                }
            } else {
                endMark = ((SequenceNode) entry).getValue().get(0).getEndMark();
                if (startMark.isPresent() && endMark.isPresent()) {
                    int line = startMark.get().getLine();
                    int column = startMark.get().getColumn();
                    int startOffset = startMark.get().getIndex();
                    int endOffset = endMark.get().getIndex();
                    return new Location(line, column, startOffset, endOffset);
                }
            }
            return new Location();
        } else {
            startMark = ((ScalarNode) entry).getStartMark();
            endMark = ((ScalarNode) entry).getEndMark();
            if (startMark.isPresent() && endMark.isPresent()) {
                int line = startMark.get().getLine();
                int column = startMark.get().getColumn();
                int startOffset = startMark.get().getIndex();
                int endOffset = endMark.get().getIndex();
                return new Location(line, column, startOffset, endOffset);
            }
        }
        return new Location();
    }

}
