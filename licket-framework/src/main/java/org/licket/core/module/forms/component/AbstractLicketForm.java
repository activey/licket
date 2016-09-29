package org.licket.core.module.forms.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.licket.core.model.LicketModel;
import org.licket.core.module.application.LicketRemoteCommunication;
import org.licket.core.view.ComponentView;
import org.licket.core.view.container.AbstractLicketContainer;
import org.licket.core.view.hippo.testing.annotation.AngularClassFunction;
import org.licket.core.view.hippo.testing.annotation.AngularComponent;
import org.licket.core.view.hippo.testing.annotation.Name;
import org.licket.core.view.render.ComponentRenderingContext;
import org.licket.framework.hippo.BlockBuilder;
import org.licket.framework.hippo.ObjectLiteralBuilder;
import org.licket.framework.hippo.ObjectPropertyBuilder;
import org.mozilla.javascript.Parser;
import org.mozilla.javascript.ast.AstRoot;
import org.mozilla.javascript.ast.ObjectLiteral;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import static com.fasterxml.jackson.core.JsonGenerator.Feature.QUOTE_FIELD_NAMES;
import static java.lang.String.format;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.ObjectLiteralBuilder.objectLiteral;
import static org.licket.framework.hippo.ObjectPropertyBuilder.propertyBuilder;
import static org.licket.framework.hippo.PropertyNameBuilder.property;

/**
 * @author activey
 */
@AngularComponent
public abstract class AbstractLicketForm<T> extends AbstractLicketContainer<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractLicketForm.class);

    @Autowired
    @Name("licketRemote")
    private LicketRemoteCommunication communicationService;

    @Name("formModel")
    private ObjectPropertyBuilder modelProperty = propertyBuilder();

    public AbstractLicketForm(String id, Class<T> modelClass, LicketModel<T> model, ComponentView componentView) {
        super(id, modelClass, model, componentView);
    }

    @Override
    protected void onInitializeContainer() {
        // setting up initial model value from model object
        modelProperty.value(componentInitialModel());
    }

    private ObjectLiteralBuilder componentInitialModel() {
        ObjectLiteralBuilder objectLiteralBuilder = objectLiteral();
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(QUOTE_FIELD_NAMES, false);

            // serialize component model to string json
            String modelStringValue = mapper.writeValueAsString(getComponentModel().get());

            // parse model declaration object literal
            AstRoot astRoot = new Parser().parse(modelObjectLiteralReader(modelStringValue), "test.js", 0);
            astRoot.visitAll(node -> {
                if (node instanceof ObjectLiteral) {
                    objectLiteralBuilder.fromObjectLiteral((ObjectLiteral) node);
                    return false;
                }
                return true;
            });
        } catch (IOException e) {
            LOGGER.error("An error occurred while creating Angular class constructor.", e);
        }
        return objectLiteralBuilder;
    }

    private Reader modelObjectLiteralReader(String modelStringValue) {
        return new StringReader(format("model = %s", modelStringValue));
    }

    protected void onSubmit(T formModelObject) {}

    @Override
    protected void onRender(ComponentRenderingContext renderingContext) {
        renderingContext.onSurfaceElement(element -> {
            element.setAttribute("(ngSubmit)", "submitForm()");
        });
    }

    @AngularClassFunction
    public void submitForm(BlockBuilder functionBlock) {
        functionBlock.appendStatement(
                expressionStatement(
                        functionCall()
                                .target(property(name("licketRemote"), name("invokeComponentAction")))
                                .argument(objectLiteral())
                )
        );
    }
}
