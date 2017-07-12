package org.licket.spring.resource;

import org.licket.core.resource.ResourceStorage;
import org.licket.core.resource.patch.FastJsonPatchResource;
import org.licket.core.resource.vue.VueLibraryResource;
import org.licket.core.resource.vue.VueRouterLibraryResource;
import org.licket.core.resource.vue.boot.VueGlobalInitializationResource;
import org.licket.core.resource.vue.boot.VueInstanceInitializerResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.annotation.SessionScope;

/**
 * @author activey
 */
@Configuration
public class LicketResourcesConfiguration {

    @Bean
    @SessionScope
    public ResourceStorage resourcesStorage() {
        return new SpringResourceStorage();
    }

    @Bean
    @Order(1)
    public VueLibraryResource vueLibraryResource() {
        return new VueLibraryResource();
    }

    @Bean
    @Order(2)
    public VueRouterLibraryResource vueRouterLibraryResource() {
        return new VueRouterLibraryResource();
    }

    @Bean
    @Order(3)
    @SessionScope
    public VueGlobalInitializationResource globalInitializationResource() {
        return new VueGlobalInitializationResource();
    }

    @Bean
    @Order(4)
    @SessionScope
    public VueInstanceInitializerResource vueApplicationModuleResource() {
        return new VueInstanceInitializerResource();
    }

    @Bean
    @Order(5)
    public FastJsonPatchResource jsonPatchResource() {
        return new FastJsonPatchResource();
    }
}
