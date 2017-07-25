package org.licket.semantic.component.loader;

import org.licket.core.view.LicketStaticLabel;
import org.licket.core.view.container.AbstractLicketMonoContainer;

import static org.licket.core.model.LicketComponentModel.ofString;
import static org.licket.core.view.LicketComponentView.fromComponentClass;

/**
 * @author lukaszgrabski
 */
public class SemanticUILoader extends AbstractLicketMonoContainer<String> {

  public SemanticUILoader(String id, String label) {
    super(id, String.class, ofString(label), fromComponentClass(SemanticUILoader.class));
  }

  @Override
  protected void onInitializeContainer() {
    add(new LicketStaticLabel("loader-content", ofString(getComponentModel().get())));
  }
}
