package org.licket.spring.web;

import com.fasterxml.jackson.databind.JsonNode;
import org.licket.core.LicketApplication;
import org.licket.core.model.LicketComponentModelGroup;
import org.licket.core.view.ComponentActionCallback;
import org.licket.core.view.LicketComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

import static org.licket.core.LicketUrls.CONTEXT_FORM;
import static org.licket.core.id.CompositeId.fromStringValue;
import static org.licket.spring.web.ComponentNotFoundException.componentNotFound;
import static org.licket.spring.web.component.ComponentActionHandler.onComponent;

@Controller
@RequestMapping(CONTEXT_FORM)
public class LicketFormController {

  @Autowired
  private LicketApplication licketApplication;

  @PostMapping(value = "/submit/{formComponentCompositeId}", produces = "application/json")
  public @ResponseBody
  LicketComponentModelGroup submitForm(@RequestBody JsonNode formData,
                                       @PathVariable String formComponentCompositeId) {
    Optional<LicketComponent<?>> form = licketApplication.findComponent(fromStringValue(formComponentCompositeId));
    if (!form.isPresent()) {
      throw componentNotFound(formComponentCompositeId);
    }

    // component callback
    ComponentActionCallback componentActionCallback = new ComponentActionCallback();

    // submitting form component
    onComponent(form.get()).trySubmitForm(formData, componentActionCallback);

    // refreshing form model after submit
    LicketComponentModelGroup modelGroup = new LicketComponentModelGroup();
    modelGroup.addModel(form.get().getCompositeId().getValue(), form.get().getComponentModel().get());

    // sending back list of reloaded component models
    return modelGroup.collectModels(componentActionCallback);
  }
}
