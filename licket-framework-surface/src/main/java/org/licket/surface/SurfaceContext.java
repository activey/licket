package org.licket.surface;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.licket.core.id.CompositeId;
import org.licket.surface.tag.ElementFactories;
import org.licket.xml.Builder;
import org.licket.xml.ParsingException;
import org.licket.xml.dom.Document;

import static org.licket.surface.SurfaceProcessingException.contentParsingException;
import static org.licket.surface.SurfaceProcessingException.ioException;

/**
 * 
 * @author activey
 */
public class SurfaceContext {

    private final ElementFactories elementFactories;
    private final CompositeId parentSurfaceContextRootId;
    private boolean skipComments;

    public SurfaceContext(ElementFactories elementFactories, CompositeId parentSurfaceContextRootId) {
        this.elementFactories = elementFactories;
        this.parentSurfaceContextRootId = parentSurfaceContextRootId;
    }

    public SurfaceContext(ElementFactories elementFactories) {
        this(elementFactories, null);
    }

    public final void processTemplateContent(InputStream templateContent, OutputStream output) {
        Builder builder = new Builder(new SurfaceNodeFactory(this, elementFactories));
        try {
            Document document = builder.build(templateContent);
            document.toXML(output);
        } catch (ParsingException e) {
            throw contentParsingException(e);
        } catch (IOException ie) {
            throw ioException(ie);
        }
    }

    public final boolean isSubContext() {
        return parentSurfaceContextRootId != null;
    }

    public final boolean isSkipComments() {
        return skipComments;
    }

    public final CompositeId getParentSurfaceContextRootId() {
        return parentSurfaceContextRootId;
    }

    public final SurfaceContext subContext(CompositeId parentContextId) {
        return new SurfaceContext(elementFactories, parentContextId);
    }
}
