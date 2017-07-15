package org.licket.demo.service;

import com.github.javafaker.Faker;
import com.github.javafaker.Internet;
import org.licket.demo.model.Contact;
import org.licket.demo.model.EmailAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.IdGenerator;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.google.common.collect.Iterables.getFirst;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.synchronizedList;

@Service
public class ContactsService {

    private static final String PICTURE_URL = "https://api.adorable.io/avatars/200/%s.png";
    private Faker faker = new Faker();
    @Autowired
    private IdGenerator idGenerator;

    private List<Contact> contacts = synchronizedList(newArrayList());

    @PostConstruct
    private void initContacts() {
        contacts = newArrayList(
                randomContact()
        );
    }

    public List<Contact> getAllContacts() {
        return contacts;
    }

    public Contact contact(Internet internet, String name, String description) {
        Contact contact = new Contact(idGenerator.generateId().toString(), name, description);
        contact.addEmail(idGenerator.generateId().toString(), internet.emailAddress());
        contact.addEmail(idGenerator.generateId().toString(), internet.emailAddress());
        contact.addEmail(idGenerator.generateId().toString(), internet.emailAddress());
        return contact;
    }

    public Contact emptyContact() {
        Contact contact = new Contact();
        contact.addEmail(idGenerator.generateId().toString(), faker.internet().emailAddress());
        contact.setPictureUrl(String.format(PICTURE_URL, getFirst(contact.getEmails(), new EmailAddress(idGenerator.generateId().toString())).getValue()));
        return contact;
    }

    public Contact randomContact() {
        Contact contact = contact(faker.internet(), faker.name().fullName(), faker.lorem().paragraph());
        contact.setContent(faker.lorem().sentence(100));
        contact.setPictureUrl(String.format(PICTURE_URL, getFirst(contact.getEmails(), new EmailAddress(idGenerator.generateId().toString())).getValue()));
        return contact;
    }

    public Optional<Contact> getContactById(String contactId) {
        return contacts.stream().filter(contact -> contact.getId().equals(contactId)).findFirst();
    }

    public void addContact(Contact contact) {
        contact.setId(idGenerator.generateId().toString());
        contacts.add(0, contact);
    }

    public boolean deleteContactById(String contactId) {
        return contacts.removeIf(contact -> contact.getId().equals(contactId));
    }
}
