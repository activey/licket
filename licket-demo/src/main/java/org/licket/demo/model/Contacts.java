package org.licket.demo.model;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author activey
 */
public class Contacts {

    public static Contacts fromIterable(Iterable<Contact> allContacts) {
        return new Contacts(allContacts);
    }

    private Contacts(Iterable<Contact> allContacts) {
        contacts = newArrayList(allContacts);
    }

    private List<Contact> contacts = newArrayList();

    public final void addContact(Contact contact) {
        contacts.add(contact);
    }

    public final List<Contact> getContacts() {
        return contacts;
    }
}
