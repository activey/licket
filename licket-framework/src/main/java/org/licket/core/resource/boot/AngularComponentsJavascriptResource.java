package org.licket.core.resource.boot;

import org.licket.core.LicketApplication;
import org.licket.core.resource.HeadParticipatingResource;
import org.licket.core.resource.javascript.AbstractJavascriptDynamicResource;
import org.licket.core.view.DefaultComponentVisitor;
import org.licket.core.view.container.LicketComponentContainer;
import org.licket.framework.hippo.BlockBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import static org.licket.core.view.angular.ClassConstructorBuilder.constructorBuilder;
import static org.licket.core.view.angular.ComponentBuilder.component;
import static org.licket.core.view.angular.ComponentClassBuilder.classBuilder;

/**
 * @author activey
 */
public class AngularComponentsJavascriptResource extends AbstractJavascriptDynamicResource implements HeadParticipatingResource {

    @Autowired
    private LicketApplication licketApplication;

    @Override
    public String getName() {
        return "Licket.components.js";
    }

    @Override
    protected void buildJavascriptTree(BlockBuilder scriptBlockBuilder) {
        licketApplication.traverseDown(new DefaultComponentVisitor() {

            @Override
            public void visitComponentContainer(LicketComponentContainer<?> container) {
                generateComponentContainerCode(scriptBlockBuilder, container);
            }
        });
    }

    private void generateComponentContainerCode(BlockBuilder scriptBlockBuilder, LicketComponentContainer<?> container) {
        if (!container.getComponentContainerView().isExternalized()) {
            return;
        }

        scriptBlockBuilder.statement(component()
                .selector(container.getId())
                // TODO read component resource url!!!
                .templateUrl("/licket/component/contacts-page/view")
                .componentName(container.getCompositeId().getNormalizedValue())
                .clazz(classBuilder()
                        .constructor(constructorBuilder())));
    }
}
