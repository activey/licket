package org.licket.core.view;

import static com.google.common.collect.Lists.newArrayList;
import static org.licket.core.model.LicketModel.empty;
import static org.licket.core.view.LicketComponentView.fromCurrentMarkup;
import java.util.List;
import org.licket.core.id.CompositeId;
import org.licket.core.model.LicketModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import javax.annotation.PostConstruct;

public abstract class AbstractLicketComponent<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractLicketComponent.class);

    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    private String id;
    private LicketComponentView componentView;

    private LicketModel<T> componentModel;

    private List<AbstractLicketComponent<?>> children = newArrayList();
    private AbstractLicketComponent<?> parent;

    public AbstractLicketComponent(String id) {
        this(id, fromCurrentMarkup(), empty());
    }

    public AbstractLicketComponent(String id, LicketComponentView componentView) {
        this(id, componentView, empty());
    }

    public AbstractLicketComponent(String id, LicketComponentView componentView, LicketModel<T> componentModel) {
        this.id = id;
        this.componentView = componentView;
        this.componentModel = componentModel;
    }

    @PostConstruct
    private final void initialize() {
        children.stream().forEach(this::initializeChildComponent);
        onInitialize();
    }

    private void initializeChildComponent(AbstractLicketComponent<?> licketComponent) {
        LOGGER.debug("Initializing child component: {}", licketComponent.getId());
        beanFactory.autowireBean(licketComponent);
        licketComponent.initialize();
    }

    protected void onInitialize() {}

    protected void add(AbstractLicketComponent<?> licketComponent) {
        licketComponent.setParent(this);
        children.add(licketComponent);
    }

    public LicketModel<T> getComponentModel() {
        return componentModel;
    }

    public void setComponentModel(LicketModel<T> componentModel) {
        this.componentModel = componentModel;
    }

    public void setComponentModelObject(T componentModelObject) {
        componentModel.set(componentModelObject);
    }

    public void setParent(AbstractLicketComponent<?> parent) {
        this.parent = parent;
    }

    public AbstractLicketComponent<?> findChild(CompositeId compositeId) {
        String currentId = compositeId.next();
        if (this.id.equals(currentId) && !compositeId.hasMore()) {
            return this;
        }
        for (AbstractLicketComponent<?> child : children) {
            if (!compositeId.hasMore()) {
                return null;
            }
            AbstractLicketComponent<?> childComponent = child.findChild(compositeId);
            if (childComponent != null) {
                return childComponent;
            }
        }
        return null;
    }

    public LicketComponentView getComponentView() {
        return componentView;
    }

    public String getId() {
        return id;
    }
}
