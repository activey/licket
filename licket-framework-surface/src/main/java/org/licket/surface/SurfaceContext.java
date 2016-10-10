package org.licket.surface;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.licket.surface.tag.ElementFactories;
import org.licket.xml.Builder;
import org.licket.xml.ParsingException;
import org.licket.xml.dom.Document;

import static org.licket.surface.SurfaceProcessingException.contentParsingException;
import static org.licket.surface.SurfaceProcessingException.ioException;
import static org.licket.surface.tag.ElementFactories.serviceLoaderFactories;

/**
 * 
 * @author activey
 */
public class SurfaceContext {

    private final ElementFactories elementFactories;
    private boolean skipComments;

    public SurfaceContext() {
        this(serviceLoaderFactories());
    }

    public SurfaceContext(ElementFactories elementFactories) {
        this.elementFactories = elementFactories;
    }

    public void processTemplateContent(InputStream templateContent, OutputStream output) {
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

    public boolean isSkipComments() {
        return skipComments;
    }

    public void setSkipComments(boolean skipComments) {
        this.skipComments = skipComments;
    }
}
