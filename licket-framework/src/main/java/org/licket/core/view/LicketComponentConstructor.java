package org.licket.core.view;

import static org.licket.core.LicketApplicationLoadingException.missingConstructorError;
import static org.licket.core.model.LicketModel.empty;
import static org.licket.core.view.LicketComponentView.fromComponentClass;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.licket.core.LicketApplicationLoadingException;
import org.licket.core.model.LicketModel;

/**
 * @author activey
 */
public class LicketComponentConstructor<T extends AbstractLicketComponent<?>> {

    private Class<T> componentClass;

    private LicketComponentConstructor(Class<T> componentClass) {
        this.componentClass = componentClass;
    }

    public static <S extends AbstractLicketComponent<?>> LicketComponentConstructor<S> constructor(Class<S> componentClass) {
        return new LicketComponentConstructor<S>(componentClass);
    }

    public T construct(String componentId) {
        try {
            Constructor<T> componentConstructor = componentClass.getDeclaredConstructor(String.class,
                LicketComponentView.class, LicketModel.class);
            if (componentConstructor == null) {
                throw missingConstructorError();
            }
            return componentConstructor.newInstance(componentId, fromComponentClass(componentClass), empty());
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException
                | InvocationTargetException e) {
            throw new LicketApplicationLoadingException(e);
        }
    }

    public <S extends AbstractLicketComponent<?>> T construct() {
        return construct(generateComponentId(componentClass));
    }

    private String generateComponentId(Class<T> componentClass) {
        return componentClass.getName();
    }

}
