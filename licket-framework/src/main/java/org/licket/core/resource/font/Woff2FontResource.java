package org.licket.core.resource.font;

import org.licket.core.resource.AbstractClasspathResource;

/**
 * @author grabslu
 */
public class Woff2FontResource extends AbstractClasspathResource {

    public static final String FONT_MIMETYPE = "application/font-woff2";
    private String name;

    public Woff2FontResource(String name, String classpathLocation) {
        super(classpathLocation, FONT_MIMETYPE);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
