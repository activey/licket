package org.licket.demo.model;

/**
 * @author grabslu
 */
public class EmailAddress {

    private String id;
    private String value;

    public EmailAddress() {

    }

    public EmailAddress(String id) {
        this.id = id;
    }

    public EmailAddress(String id, String emailAddress) {
        this(id);
        this.value = emailAddress;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
