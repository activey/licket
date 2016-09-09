package org.licket.surface;

import org.junit.Ignore;
import org.junit.Test;

import java.io.InputStream;

/**
 * @author activey
 */
@Ignore
public class SurfaceIntegrationTest {

    @Test
    public void test() throws Exception {
        // given
        SurfaceContext surfaceContext = new SurfaceContext();
        surfaceContext.processTemplateContent(readTemplate("TestPage.html"), System.out, true);
    }

    private InputStream readTemplate(String templateLocation) {
        return SurfaceIntegrationTest.class.getClassLoader().getResourceAsStream(templateLocation);
    }
}
