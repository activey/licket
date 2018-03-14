package org.licket.core.resource.javascript;

import com.google.common.io.CharStreams;
import org.licket.core.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stringtemplate.v4.ST;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.net.MediaType.JAVASCRIPT_UTF_8;

/**
 * @author lukaszgrabski
 */
public class JavascriptTemplateResource implements Resource {

  private final static Logger LOGGER = LoggerFactory.getLogger(JavascriptTemplateResource.class);

  private final String name;
  private final String templateClasspathLocation;

  public JavascriptTemplateResource(String name, String templateClasspathLocation) {
    this.name = name;
    this.templateClasspathLocation = templateClasspathLocation;
  }

  @Override
  public String getMimeType() {
    return JAVASCRIPT_UTF_8.toString();
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public InputStream getStream() {
    InputStream templateStream = JavascriptTemplateResource.class.getClassLoader().getResourceAsStream(templateClasspathLocation);
    if (templateStream == null) {
      LOGGER.error("Unable to locate ClassPath resource: {}", templateClasspathLocation);
      return null;
    }
    try {
      ST stringTemplate = new ST(CharStreams.toString(new InputStreamReader(templateStream)));
      Map<String, Object> parameters = newHashMap();
      collectTemplateVariables(parameters);
      parameters.entrySet().forEach(entry -> stringTemplate.add(entry.getKey(), entry.getValue()));
      return new ByteArrayInputStream(stringTemplate.render().getBytes());
    } catch (IOException e) {
      LOGGER.error("Unable to process template.", e);
    }
    return null;
  }

  protected void collectTemplateVariables(Map<String, Object> templateVariables) {
  }
}
