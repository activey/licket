package org.licket.spring.web.component;

import java.io.Serializable;

/**
 * @author activey
 */
public class ComponentActionRequest implements Serializable {

    private String compositeId;
    private String childCompositeId;

    public String getCompositeId() {
        return compositeId;
    }

    public void setCompositeId(String compositeId) {
        this.compositeId = compositeId;
    }

    public String getChildCompositeId() {
        return childCompositeId;
    }

    public void setChildCompositeId(String childCompositeId) {
        this.childCompositeId = childCompositeId;
    }
}
