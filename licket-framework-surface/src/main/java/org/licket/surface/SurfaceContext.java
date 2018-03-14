package org.licket.surface;

import org.licket.core.id.CompositeId;
import org.licket.surface.tag.ElementFactories;
import org.licket.xml.Builder;
import org.licket.xml.ParsingException;
import org.licket.xml.dom.Document;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static org.licket.surface.SurfaceProcessingException.contentParsingException;
import static org.licket.surface.SurfaceProcessingException.ioException;

/**
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

  public final void processTemplateContent(InputStream templateContent, OutputStream output) {
    try {
      Document document = processTemplateContent(templateContent);
      document.toXML(output);
    } catch (ParsingException e) {
      throw contentParsingException(e);
    } catch (IOException ie) {
      throw ioException(ie);
    }
  }

  public final Document processTemplateContent(InputStream templateContent) {
    try {
      Builder builder = new Builder(new SurfaceNodeFactory(this, elementFactories));
      return builder.build(templateContent);
    } catch (ParsingException e) {
      throw contentParsingException(e);
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
}
