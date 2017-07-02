package org.licket.demo.service;

import com.github.javafaker.Faker;
import com.github.javafaker.Internet;
import org.licket.demo.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.IdGenerator;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static com.google.common.collect.Lists.newArrayList;
import static org.apache.commons.collections.ListUtils.synchronizedList;

@Service
public class ContactsService {
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
        contact.addEmail(internet.emailAddress());
        contact.addEmail(internet.emailAddress());
        contact.addEmail(internet.emailAddress());
        return contact;
    }

    public Contact emptyContact() {
        return new Contact();
    }

    public Contact randomContact() {
        Contact contact = contact(faker.internet(), faker.name().fullName(), faker.lorem().paragraph());
        contact.setContent(faker.lorem().sentence(100));
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
