package org.licket.demo.model;

/**
 * @author grabslu
 */
public class EmailAddress {

    private String value;

    public EmailAddress() {}

    public EmailAddress(String emailAddress) {
        this.value = emailAddress;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
