package org.licket.core.resource.css;

import org.licket.core.resource.AbstractClasspathResource;

/**
 * @author grabslu
 */
public class StylesheetResource extends AbstractClasspathResource {

    public static final String CSS_MIMETYPE = "text/css";
    private String name;

    public StylesheetResource(String name, String classpathLocation) {
        super(classpathLocation, CSS_MIMETYPE);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
