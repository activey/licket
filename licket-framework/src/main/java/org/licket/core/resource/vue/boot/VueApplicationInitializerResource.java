package org.licket.core.resource.vue.boot;

import org.licket.core.LicketApplication;
import org.licket.core.resource.FootParticipatingResource;
import org.licket.core.resource.ResourceStorage;
import org.licket.core.resource.javascript.AbstractJavascriptDynamicResource;
import org.licket.core.view.hippo.vue.component.VueComponentPropertiesDecorator;
import org.licket.framework.hippo.BlockBuilder;
import org.licket.framework.hippo.ObjectLiteralBuilder;
import org.licket.framework.hippo.ObjectPropertyBuilder;
import org.licket.framework.hippo.StringLiteralBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import static java.lang.String.format;
import static org.licket.framework.hippo.AssignmentBuilder.assignment;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.NewExpressionBuilder.newExpression;
import static org.licket.framework.hippo.ObjectLiteralBuilder.objectLiteral;
import static org.licket.framework.hippo.ObjectPropertyBuilder.propertyBuilder;
import static org.licket.framework.hippo.PropertyNameBuilder.property;
import static org.licket.framework.hippo.StringLiteralBuilder.stringLiteral;

/**
 * @author activey
 */
public class VueApplicationInitializerResource extends AbstractJavascriptDynamicResource implements FootParticipatingResource {

    @Autowired
    private LicketApplication application;

    @Autowired
    private ResourceStorage resourceStorage;

    @Override
    public String getName() {
        return "Licket.application.js";
    }

    @Override
    protected void buildJavascriptTree(BlockBuilder scriptBlockBuilder) {
        scriptBlockBuilder.appendStatement(
            expressionStatement(assignment()
                    .left(property(name("app"), name("instance")))
                    .right(newExpression()
                            .target(name("Vue"))
                            .argument(vueInstanceProperties())))
        );

    }

    private ObjectLiteralBuilder vueInstanceProperties() {
        return objectLiteral()
                .objectProperty(mountPoint())
                .objectProperty(componentsTree());
    }

    private ObjectPropertyBuilder componentsTree() {
        ObjectLiteralBuilder components = objectLiteral();
        application.traverseDown(container -> {
            new VueComponentPropertiesDecorator(container, resourceStorage).decorate(components);
            return false;
        });
        return propertyBuilder().name("components").value(components);
    }

    private ObjectPropertyBuilder mountPoint() {
        return propertyBuilder().name("el").value(applicationRootId());
    }

    private StringLiteralBuilder applicationRootId() {
        return stringLiteral(format("#%s", application.rootComponentContainer().getId()));
    }
}
