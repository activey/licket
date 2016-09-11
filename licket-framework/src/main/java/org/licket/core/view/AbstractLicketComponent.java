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

public abstract class AbstractLicketComponent<T> implements LicketComponent<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractLicketComponent.class);

    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    private String id;
    private LicketComponentView componentView;

    private LicketModel<T> componentModel;

    private List<LicketComponent<?>> children = newArrayList();
    private LicketComponent<?> parent;

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
    public final void initialize() {
        children.stream().forEach(this::initializeChildComponent);
        onInitialize();
    }

    private void initializeChildComponent(LicketComponent<?> licketComponent) {
        LOGGER.debug("Initializing child component: {}", licketComponent.getId());
        beanFactory.autowireBean(licketComponent);
        licketComponent.initialize();
    }

    protected void onInitialize() {}

    protected void add(LicketComponent<?> licketComponent) {
        licketComponent.setParent(this);
        children.add(licketComponent);
    }

    @Override
    public LicketModel<T> getComponentModel() {
        return componentModel;
    }

    @Override
    public void setComponentModel(LicketModel<T> componentModel) {
        this.componentModel = componentModel;
    }

    @Override
    public void setComponentModelObject(T componentModelObject) {
        componentModel.set(componentModelObject);
    }

    @Override
    public void setParent(LicketComponent<?> parent) {
        this.parent = parent;
    }

    @Override
    public LicketComponent<?> findChild(CompositeId compositeId) {
        String currentId = compositeId.next();
        if (this.id.equals(currentId) && !compositeId.hasMore()) {
            return this;
        }
        if (!compositeId.hasMore()) {
            return null;
        }
        for (LicketComponent<?> child : children) {
            LicketComponent<?> childComponent = child.findChild(compositeId);
            if (childComponent != null) {
                return childComponent;
            }
        }
        return null;
    }

    @Override
    public LicketComponentView getComponentView() {
        return componentView;
    }

    @Override
    public String getId() {
        return id;
    }
}
