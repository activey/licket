package org.licket.framework.hippo.testing;

/**
 * @author grabslu
 */
public class LicketRemoteCommunication implements AngularClass {

    private Dependency http;

    public LicketRemoteCommunication(@Name("ng.http.Http") Dependency http) {
        this.http = http;
    }

}
