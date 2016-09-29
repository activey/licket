package org.licket.core.view.link;

import static java.lang.String.format;
import java.util.Optional;
import org.licket.core.view.AbstractLicketComponent;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.form.AbstractLicketForm;
import org.licket.core.view.render.ComponentRenderingContext;
import org.licket.xml.dom.Comment;

/**
 * @author activey
 */
public final class LicketFormSubmitButton<T> extends AbstractLicketComponent<Void> {

    public LicketFormSubmitButton(String id) {
        super(id, Void.class);
    }

    @Override
    protected void onRender(ComponentRenderingContext renderingContext) {
        Optional<LicketComponent<?>> parentForm = getEnclosingForm();
        if (!parentForm.isPresent()) {
            renderingContext.onSurfaceElement(element -> element
                .replaceWith(new Comment("Unable to find parent form matching submit button: %s.", getId())));
            return;
        }
        renderingContext.onSurfaceElement(surfaceElement -> surfaceElement.setAttribute("(click)",
            format("submitForm()", getCompositeId().getValue())));
    }

    private Optional<LicketComponent<?>> getEnclosingForm() {
        return traverseUp(component -> {
            if (component instanceof AbstractLicketForm) {
                AbstractLicketForm form = (AbstractLicketForm) component;
                if (form.getComponentModelClass().equals(getComponentModelClass())) {
                    return true;
                }
            }
            return false;
        });
    }
}
