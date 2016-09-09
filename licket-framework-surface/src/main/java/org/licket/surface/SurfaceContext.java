package org.licket.surface;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.ParsingException;
import nu.xom.Serializer;
import org.licket.surface.tag.ElementFactories;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.licket.surface.SurfaceProcessingException.contentParsingException;
import static org.licket.surface.SurfaceProcessingException.ioException;
import static org.licket.surface.tag.ElementFactories.serviceLoaderFactories;

/**
 * 
 * @author activey
 */
public class SurfaceContext {

    private final SurfaceContext parentContext;
    private ElementFactories elementFactories;
    private boolean skipComments;

    private static final Logger LOGGER = LoggerFactory.getLogger(SurfaceContext.class);

    public SurfaceContext() {
        this(serviceLoaderFactories());
    }

    public SurfaceContext(ElementFactories elementFactories) {
        this(elementFactories, null);
    }

    public SurfaceContext(ElementFactories elementFactories, SurfaceContext parentContext) {
        this.parentContext = parentContext;
        this.elementFactories = elementFactories;
    }

    public void processTemplateContent(InputStream templateContent, OutputStream output, boolean preserveNamespace) {
        Builder builder = new Builder(new SurfaceNodeFactory(this, elementFactories));
        try {
            Document document = builder.build(templateContent);
            serializeDocument(document, output, preserveNamespace);
        } catch (ParsingException e) {
            SurfaceProcessingException se = contentParsingException(e);
            LOGGER.error("", se);
            throw se;
        } catch (IOException e) {
            throw ioException(e);
        }
    }

    private void serializeDocument(Document document, OutputStream output, boolean preserveNamespace) throws IOException {
        Serializer serializer = new SurfaceContentSerializer(output, preserveNamespace);
        serializer.setIndent(4);
        serializer.setPreserveBaseURI(false);
        serializer.write(document);
        serializer.flush();
    }

    public boolean isSkipComments() {
        return skipComments;
    }

    public void setSkipComments(boolean skipComments) {
        this.skipComments = skipComments;
    }
}
