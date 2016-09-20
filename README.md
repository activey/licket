 ![](https://rawgit.com/activey/licket/master/licket.svg)
# Licket baby!
Here it is, brand new sweets to make your life even sweeter than before. Yes you! my brave web developer ;)

What is Licket you ask? Licket is a Java based, Spring Boot driven a AngularJS 2 flavoured stack!
While being influenced a lot by Apache Wicket (http://wicket.apache.org, I love you guys...) introduces a little more Angularish way of living :P

## Step by step HOW-TO or how to get running NOW!

First, grab this repo code and build all by yourself on your local beast or ask build automation to do it for you.
Take this dependency with you:

```
<dependency>
    <groupId>org.licket</groupId>
    <artifactId>licket-spring-autoconfigure</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

Let's start with simple model first:

```
public class Contact {

    private String name;
    private String description;
    
    ... getters/setters ...
}
    
```

Then a simple Spring service providing some data:

```
@Service
public class ContactsService {

    public List<Contact> getAllContacts() {
        return newArrayList(
                contact("picture1.jpg", "Chuck Norris", "That's him."),
                contact("picture2.jpg", "Jonh Doe", "Lorem ipsum"),
                contact("picture3.jpg", "Bob Marley", "Je je je")
        );
    }

}
```

As mentioned in preface, Licket derives many concepts from Apache Wicket like logic less HTML templates. You can define view in very similar way:

```
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml" xmlns:lick="http://www.w3.org/1999/xhtml">
    <head>
            <title>Contacts Page</title>
    </head>
    <body>
        <h1>Contacts list</h1>

        <div lick:id="contacts-panel">
            <div lick:id="contact">
                Name: <span lick:id="name">here will be name</span>
                Description: <span lick:id="description">and here description</span>
            </div>
        </div>
    </body>
</html>
```

Then you can model out the components:

```
public class ContactsAppRoot extends AbstractLicketContainer<Void> {

  public ContactsAppRoot(String id,
      @Autowired @Qualifier("contactsPanel") LicketComponentContainer contactsPanel) {
    super(id, fromComponentContainerClass(ContactsAppRoot.class));

    add(contactsPanel);
  }
}

public class ContactsPanel extends AbstractLicketContainer<Contacts> {

    @Autowired
    private ContactsService contactsService;

    public ContactsPanel(String id) {
        super(id, fromComponentContainerClass(ContactsPanel.class));

        add(new ContactsList("contact", new LicketModel("contacts")));
    }

    @Override
    protected void onInitializeContainer() {
        readContacts();
    }

    private void readContacts() {
        setComponentModelObject(fromIterable(contactsService.getAllContacts()));
    }
}

public ContactsList(String id, LicketModel<String> enclosingComponentPropertyModel) {
        super(id, enclosingComponentPropertyModel, Contact.class);

        add(new LicketLabel("name"));
        add(new LicketLabel("description"));
    }
```

Next,coin your own Spring Boot configuration class and glue all together:

```
@Configuration
public class LicketConfiguration {

    @LicketComponent("root") // there need to be one container component called "root"
    public LicketComponentContainer root() {
        return new ContactsAppRoot("contacts-page", contactsPanel());
    }

    @LicketComponent("contactsPanel")
    public LicketComponentContainer contactsPanel() {
        return new ContactsPanel("contacts-panel");
    }
    
    @Bean
    public ContactsService contactsService() {
        return new ContactsService();
    }
    
    ... other beans ...

}
```

Feel the magic :sparkles:
 
