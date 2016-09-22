package org.licket.core.resource.javascript;

import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.String.format;
import static java.util.Collections.enumeration;
import static org.licket.core.resource.javascript.JavascriptStaticResource.JAVASCRIPT_MIMETYPE;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.SequenceInputStream;
import org.licket.core.resource.Resource;

/**
 * @author activey
 */
public class MergedJavascriptStaticResource implements Resource {

    private String name;
    private JavascriptStaticResource[] resources;

    public MergedJavascriptStaticResource(String name, JavascriptStaticResource... resources) {
        this.name = name;
        this.resources = resources;
    }

    @Override
    public InputStream getStream() {
        return new SequenceInputStream(enumeration(from(newArrayList(resources))
            .transform(javascriptStaticResource -> new SequenceInputStream(
                scriptHeaderStream(javascriptStaticResource.getName()), javascriptStaticResource.getStream()))
            .toList()));
    }

    private InputStream scriptHeaderStream(String name) {
        return new ByteArrayInputStream(format("\n/* ----- including %s ----- */\n", name).getBytes());
    }

    @Override
    public String getMimeType() {
        return JAVASCRIPT_MIMETYPE;
    }

    @Override
    public String getName() {
        return name;
    }
}
