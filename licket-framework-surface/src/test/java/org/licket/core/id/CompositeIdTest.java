package org.licket.core.id;

import org.junit.Test;

import static org.licket.core.id.CompositeId.fromStringValue;

/**
 * @author grabslu
 */
public class CompositeIdTest {

    @Test
    public void test() {
        // given
        CompositeId compositeId = fromStringValue("1:2:3:4");

        String id = "";
        while (compositeId.hasMore()) {
            System.out.println("current = " + compositeId.current());
            System.out.println("forward = " + compositeId.forward());
            System.out.println("-------------------");
        }
    }
}