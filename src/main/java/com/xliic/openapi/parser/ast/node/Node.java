package com.xliic.openapi.parser.ast.node;

import com.xliic.openapi.parser.ast.ParserAST;
import com.xliic.openapi.parser.ast.Range;
import org.snakeyaml.engine.v2.nodes.MappingNode;
import org.snakeyaml.engine.v2.nodes.NodeTuple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public abstract class Node implements Comparable<Node> {

    private final int depth;
    private final String key;
    private final String pointer;

    protected final Object node;
    private final Node parent;
    private final List<Node> children;

    public Node(Object node, Node parent, int depth, String pointer, String key) {
        this.node = node;
        this.parent = parent;
        this.children = new LinkedList<>();
        this.depth = depth;
        this.pointer = pointer;
        this.key = key;
    }

    @Override
    public int compareTo(Node o) {
        return getValueRange().compareTo(o.getValueRange());
    }

    public List<Node> getChildren() {
        return children;
    }

    public int getDepth() {
        return depth;
    }

    public String getJsonPointer() {
        return pointer;
    }

    public void addSortedChildren(List<Node> nodes) {
        Collections.sort(nodes);
        children.addAll(nodes);
    }

    public Node find(String pointer) {
        if (parent != null) {
            pointer = getJsonPointer() + pointer;
        }
        if (getJsonPointer().equals(pointer)) {
            return this;
        }
        List<Node> candidates = new ArrayList<>();
        candidates.add(this);
        for (int i = 0; i < candidates.size(); i++) {
            for (Node node : candidates.get(i).getChildren()) {
                if (node.getJsonPointer().equals(pointer)) {
                    return node;
                }
                if (pointer.startsWith(node.getJsonPointer())) {
                    candidates.add(node);
                }
            }
        }
        return null;
    }

    public abstract Range getValueRange();
    public abstract Range getRange();

    public boolean isObject() {
        if (node instanceof NodeTuple) {
            return ParserAST.isMap(((NodeTuple) node).getValueNode());
        }
        else if (node instanceof MappingNode) {
            return ParserAST.isMap(node);
        }
        return false;
    }
}
