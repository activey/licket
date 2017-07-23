package org.licket.semantic.component.loader;

import org.licket.core.module.application.LicketComponentModelReloader;
import org.licket.core.view.LicketStaticLabel;
import org.licket.core.view.container.AbstractLicketMonoContainer;

import static org.licket.core.model.LicketComponentModel.ofString;
import static org.licket.core.view.LicketComponentView.fromComponentClass;

/**
 * @author lukaszgrabski
 */
public class SemanticUILoader extends AbstractLicketMonoContainer<String> {

  private final LicketComponentModelReloader modelReloader;

  public SemanticUILoader(String id, String label, LicketComponentModelReloader modelReloader) {
    super(id, String.class, ofString(label), fromComponentClass(SemanticUILoader.class));
    this.modelReloader = modelReloader;
  }

  @Override
  protected void onInitializeContainer() {
    add(new LicketStaticLabel("loader-content", ofString(getComponentModel().get())));
  }

  @Override
  protected LicketComponentModelReloader getModelReloader() {
    return modelReloader;
  }
}
