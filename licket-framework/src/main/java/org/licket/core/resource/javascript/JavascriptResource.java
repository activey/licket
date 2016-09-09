package org.licket.core.resource.javascript;

import org.licket.core.resource.AbstractClasspathResource;

/**
 * @author activey
 */
public class JavascriptResource extends AbstractClasspathResource {

    public static final String JAVASCRIPT_MIMETYPE = "application/javascript";
    private final String name;

    public JavascriptResource(String name, String classpathLocation) {
        super(classpathLocation, JAVASCRIPT_MIMETYPE);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
