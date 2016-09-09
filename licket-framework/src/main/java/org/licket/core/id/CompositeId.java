package org.licket.core.id;

/**
 * @author activey
 */
public class CompositeId {

    private static final String KEY_SEPARATOR = ":";
    private String[] idParts = {};
    private int index = 0;

    public CompositeId(String compositeIdValue) {
        parseCompositeId(compositeIdValue);
    }

    private void parseCompositeId(String compositeIdValue) {
        idParts = compositeIdValue.split(KEY_SEPARATOR);
    }

    public boolean hasMore() {
        return index < idParts.length;
    }

    public String next() {
        return idParts[index++];
    }
}
