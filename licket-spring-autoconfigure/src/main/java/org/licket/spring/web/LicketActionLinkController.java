package org.licket.spring.web;

import static org.licket.core.id.CompositeId.fromStringValue;
import static org.licket.core.view.LicketUrls.CONTEXT_ACTION_LINK;
import static org.licket.spring.web.ComponentNotFoundException.componentNotFound;
import static org.licket.spring.web.component.ComponentActionHandler.onComponent;
import java.util.Optional;
import org.licket.core.LicketApplication;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.link.ComponentActionCallback;
import org.licket.core.view.model.LicketComponentModelGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(CONTEXT_ACTION_LINK)
public class LicketActionLinkController {

    @Autowired
    private LicketApplication licketApplication;

    @Deprecated
    @PostMapping(value = "/click/{linkComponentCompositeId}", produces = "application/json")
    public @ResponseBody LicketComponentModelGroup invokeComponentAction(@PathVariable String linkComponentCompositeId) {
        Optional<LicketComponent<?>> link = licketApplication.findComponent(fromStringValue(linkComponentCompositeId));
        if (!link.isPresent()) {
            throw componentNotFound(linkComponentCompositeId);
        }

        LicketComponentModelGroup modelGroup = new LicketComponentModelGroup();
        // component callback
        ComponentActionCallback componentActionCallback = new ComponentActionCallback();

        // handling action link click
        onComponent(link.get()).tryHandleLinkClick(componentActionCallback);

        // sending back list of reloaded component models
        componentActionCallback.forEachToBeReloaded(component -> modelGroup
            .addModel(component.getCompositeId().getValue(), component.getComponentModel().get()));

        return modelGroup;
    }
}
