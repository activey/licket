package org.licket.core.id;

import static com.google.common.base.Joiner.on;
import static com.google.common.collect.ObjectArrays.concat;

/**
 * @author activey
 */
public class CompositeId {

    private static final String KEY_SEPARATOR = ":";
    private String[] idParts = {};
    private int index = 0;

    public static CompositeId fromStringValueWithAdditionalParts(String compositeIdValue, String... additionalParts) {
        return new CompositeId(concat(parseCompositeId(compositeIdValue), additionalParts, String.class));
    }

    public static CompositeId fromStringValue(String compositeIdValue) {
        return new CompositeId(parseCompositeId(compositeIdValue));
    }

    private CompositeId(String... compositeIdParts) {
        idParts = compositeIdParts;
    }

    public String getValue() {
        return on(KEY_SEPARATOR).join(idParts);
    }

    public String[] getIdParts() {
        return idParts;
    }

    public boolean hasMore() {
        return index + 1 < idParts.length;
    }

    public String next() {
        return idParts[index++];
    }

    static String[] parseCompositeId(String compositeIdValue) {
        return compositeIdValue.split(KEY_SEPARATOR);
    }
}
