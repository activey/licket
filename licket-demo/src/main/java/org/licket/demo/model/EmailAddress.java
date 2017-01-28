package org.licket.demo.model;

/**
 * @author grabslu
 */
public class EmailAddress {

    private String email;

    public EmailAddress() {}

    public EmailAddress(String emailAddress) {
        this.email = emailAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
