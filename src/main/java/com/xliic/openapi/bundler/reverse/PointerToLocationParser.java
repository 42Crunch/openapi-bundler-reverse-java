package com.xliic.openapi.bundler.reverse;

import java.util.*;

public abstract class PointerToLocationParser {

    private Map<String, Location> pointers;

    protected abstract boolean isMap(Object parent);

    protected abstract boolean isList(Object parent);

    protected abstract Object getRootFromText(String text);

    protected abstract Set<Map.Entry<String, Object>> getEntrySet(Object parent);

    protected abstract Iterator<Object> getIterator(Object parent);

    protected abstract Location getLocation(Object entry);

    protected abstract Location getRootLocation(Object entry);

    protected abstract Object getChild(Object entry);

    private void clear() {
        pointers = new HashMap<>();
    }

    private void dfs(Object parent, String parentPointer) {

        if (isMap(parent)) {
            for (Map.Entry<String, Object> entry : getEntrySet(parent)) {
                String pointer = LocationUtils.pointer(parentPointer, entry.getKey());
                pointers.put(pointer, getLocation(entry.getValue()));
                dfs(getChild(entry.getValue()), pointer);
            }
        } else if (isList(parent)) {
            int index = 0;
            for (Iterator<Object> it = getIterator(parent); it.hasNext();) {
                Object o = it.next();
                String pointer = LocationUtils.pointer(parentPointer, index);
                pointers.put(pointer, getLocation(o));
                dfs(getChild(o), pointer);
                index++;
            }
        }
    }

    public Map<String, Location> parse(String text) {
        clear();
        Object root = getRootFromText(text);
        if (root != null) {
            pointers.put(LocationUtils.EMPTY_POINTER, getRootLocation(root));
            dfs(root, LocationUtils.EMPTY_POINTER);
        }
        return pointers;
    }
}
