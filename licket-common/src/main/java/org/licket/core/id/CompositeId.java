package org.licket.core.id;

import static com.google.common.base.CaseFormat.LOWER_HYPHEN;
import static com.google.common.base.CaseFormat.UPPER_CAMEL;
import static com.google.common.base.Joiner.on;
import static com.google.common.collect.Iterables.toArray;
import static com.google.common.collect.ObjectArrays.concat;
import static java.util.Arrays.stream;

import com.google.common.base.CaseFormat;
import com.google.common.base.Splitter;

/**
 * @author activey
 */
public class CompositeId {

    private static final char SEPARATOR_DEFAULT = ':';
    private static final char SEPARATOR_NORMALIZED = '_';

    private String[] idParts = {};
    private int index = 0;

    private CompositeId(String... compositeIdParts) {
        idParts = compositeIdParts;
    }

    public static CompositeId fromStringValueWithAdditionalParts(String compositeIdValue, String... additionalParts) {
        return new CompositeId(concat(parseCompositeId(compositeIdValue), additionalParts, String.class));
    }

    public static CompositeId fromStringValue(String compositeIdValue) {
        return new CompositeId(parseCompositeId(compositeIdValue));
    }

    private static String[] parseCompositeId(String compositeIdValue) {
        return toArray(Splitter.on(SEPARATOR_DEFAULT).split(compositeIdValue), String.class);
    }

    public String getValue() {
        return on(SEPARATOR_DEFAULT).join(idParts);
    }

    public String getNormalizedValue() {
        return on(SEPARATOR_NORMALIZED)
            .join(stream(idParts).map(idPart -> LOWER_HYPHEN.to(UPPER_CAMEL, idPart)).toArray());
    }

    public boolean hasMore() {
        return index + 1 < idParts.length;
    }

    public void forward() {
        index = index + 1;
    }

    public String current() {
        return idParts[index];
    }
}
