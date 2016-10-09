package org.licket.spring.web;

import static org.licket.core.id.CompositeId.fromStringValue;
import static org.licket.core.view.LicketUrls.CONTEXT_FORM;
import static org.licket.spring.web.ComponentNotFoundException.componentNotFound;
import static org.licket.spring.web.component.ComponentActionHandler.onComponent;
import java.util.Optional;
import org.licket.core.LicketApplication;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.link.ComponentActionCallback;
import org.licket.core.view.model.LicketComponentModelGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(CONTEXT_FORM)
public class LicketFormController {

    @Autowired
    private LicketApplication licketApplication;

    @Deprecated
    @PostMapping(value = "/submit/{formComponentCompositeId}", produces = "application/json")
    public @ResponseBody LicketComponentModelGroup submitForm(@RequestBody JsonNode formData,
                                                              @PathVariable String formComponentCompositeId) {
        Optional<LicketComponent<?>> form = licketApplication.findComponent(fromStringValue(formComponentCompositeId));
        if (!form.isPresent()) {
            throw componentNotFound(formComponentCompositeId);
        }

        LicketComponentModelGroup modelGroup = new LicketComponentModelGroup();
        // refreshing form model as first
        modelGroup.addModel(form.get().getCompositeId().getValue(), form.get().getComponentModel().get());

        // component callback
        ComponentActionCallback componentActionCallback = new ComponentActionCallback();

        // submitting form component
        onComponent(form.get()).trySubmitForm(formData, componentActionCallback);

        // sending back list of reloaded component models
        componentActionCallback.forEachToBeReloaded(component -> modelGroup
            .addModel(component.getCompositeId().getValue(), component.getComponentModel().get()));
        return modelGroup;
    }
}
