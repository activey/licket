package org.licket.demo.service;

import com.github.javafaker.Faker;
import com.github.javafaker.Internet;
import org.licket.demo.model.Contact;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static com.google.common.collect.Lists.newArrayList;

@Service
public class ContactsService {
    private static AtomicLong idGenerator = new AtomicLong(1);
    private Faker faker = new Faker();

    private List<Contact> contacts = newArrayList();

    @PostConstruct
    private void initContacts() {
        contacts = newArrayList(
                contact(faker.internet(), faker.name().fullName(), faker.lorem().paragraph())
        );
    }

    public List<Contact> getAllContacts() {
        return contacts;
    }

    public Contact contact(Internet internet, String name, String description) {
        Contact contact = new Contact(idGenerator.getAndIncrement(), name, description);
        contact.addEmail(internet.emailAddress());
        contact.addEmail(internet.emailAddress());
        contact.addEmail(internet.emailAddress());
        return contact;
    }

    public static Contact emptyContact() {
        return new Contact(idGenerator.getAndIncrement(), "", "");
    }

    public Optional<Contact> getContactById(long contactId) {
        return contacts.stream().filter(contact -> contact.getId() == contactId).findFirst();
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
    }
}
