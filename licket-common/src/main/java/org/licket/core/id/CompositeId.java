package org.licket.core.id;

import static com.google.common.base.CaseFormat.LOWER_HYPHEN;
import static com.google.common.base.Joiner.on;
import static com.google.common.collect.ObjectArrays.concat;
import java.util.Arrays;
import com.google.common.base.CaseFormat;

/**
 * @author activey
 */
public class CompositeId {

    private static final char KEY_SEPARATOR = ':';
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

    static String[] parseCompositeId(String compositeIdValue) {
        // TODO refactor composite id parsing
        return compositeIdValue.split("" + KEY_SEPARATOR);
    }

    public String getValue() {
        return on(KEY_SEPARATOR).join(idParts);
    }

    public String getNormalizedValue() {
        return on("_")
            .join(Arrays.stream(idParts).map(idPart -> LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL, idPart)).toArray());
    }

    public String[] getIdParts() {
        return idParts;
    }

    public boolean hasMore() {
        return index + 1 < idParts.length;
    }

    public CompositeId forward() {
        index = index + 1;
        return this;
    }

    public String current() {
        return idParts[index];
    }
}
