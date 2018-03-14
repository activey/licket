package org.licket.semantic.component.dropdown;

import org.licket.core.view.LicketLabel;
import org.licket.core.view.container.AbstractLicketMultiContainer;
import org.licket.core.view.form.LicketInput;
import org.licket.core.view.hippo.vue.annotation.OnVueMounted;
import org.licket.core.view.list.AbstractLicketList;
import org.licket.core.view.render.ComponentRenderingContext;
import org.licket.framework.hippo.BlockBuilder;
import org.licket.framework.hippo.PropertyNameBuilder;
import org.licket.surface.element.SurfaceElement;

import java.util.Optional;

import static java.lang.String.format;
import static org.licket.core.model.LicketComponentModel.emptyComponentModel;
import static org.licket.core.view.LicketComponentView.fromComponentClass;
import static org.licket.core.view.hippo.ComponentModelProperty.fromComponentModelProperty;
import static org.licket.core.view.hippo.ComponentModelProperty.fromComponentParentModelProperty;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.KeywordLiteralBuilder.thisLiteral;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.ObjectLiteralBuilder.objectLiteral;
import static org.licket.framework.hippo.PropertyNameBuilder.property;

/**
 * @author grabslu
 */
public class SemanticDropdownInput extends AbstractLicketMultiContainer<DropdownSettings> {

  private final String valueModelProperty;
  private final String dropdownElementsModelProperty;
  private final String dropdownValueProperty;

  public SemanticDropdownInput(String id, String valueModelProperty, String dropdownElementsModelProperty, String dropdownValueProperty) {
    super(id, DropdownSettings.class, emptyComponentModel(), fromComponentClass(SemanticDropdownInput.class));
    this.valueModelProperty = valueModelProperty;
    this.dropdownElementsModelProperty = dropdownElementsModelProperty;
    this.dropdownValueProperty = dropdownValueProperty;
  }

  protected void onBeforeRenderContainer(ComponentRenderingContext renderingContext) {
    renderingContext.onSurfaceElement(surfaceElement -> surfaceElement.addAttribute("class", "ui special search selection dropdown"));
  }

  @Override
  protected final void onInitializeContainer() {
    add(new LicketInput("dropdown_input", fromComponentParentModelProperty(valueModelProperty)));
    add(new AbstractLicketList("dropdown_values", fromComponentParentModelProperty(dropdownElementsModelProperty)) {

      @Override
      protected void onInitializeContainer() {
        add(new LicketLabel("dropdown_value_label", fromComponentModelProperty(dropdownValueProperty)));
      }

      @Override
      protected Optional<String> keyPropertyName() {
        return Optional.of(dropdownValueProperty);
      }

      @Override
      protected void postProcess(SurfaceElement element) {
        element.addAttribute("v-bind:data-value", format("%s.%s", getId(), keyPropertyName().get()));
      }
    });
  }

  @OnVueMounted
  public final void initializeDropdown(BlockBuilder body) {
    body.appendStatement(expressionStatement(
            functionCall()
                    .target(semanticDropdown())
                    .argument(objectLiteral())
    ));
  }

  private PropertyNameBuilder semanticDropdown() {
    return property(
            functionCall()
                    .target(name("$"))
                    .argument(property(thisLiteral(), name("$el"))),
            name("dropdown")
    );
  }
}
