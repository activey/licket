:construction: **Still in very alpha stage!** :construction:
 
<img width="200" src="https://raw.githubusercontent.com/activey/licket/master/licket.png"></img>

# Licket baby!
[![European server](http://heroku-badge.herokuapp.com/?app=licket-demo-eu&style=flat&svg=1)](https://licket-demo-eu.herokuapp.com/):eu:
[![US server](http://heroku-badge.herokuapp.com/?app=licket-demo&style=flat&svg=1)](https://licket-demo.herokuapp.com/):us:

Here it is, brand new sweets to make your life even sweeter than before. Yes you! my brave web developer ;)

What is Licket you ask? Licket is a Java based, Spring Boot driven and Vue.js flavoured stack!
While being influenced a lot by Apache Wicket (http://wicket.apache.org, I love you guys...) it brings Java/Javascript web development to a completely new level :)

## Step by step HOW-TO or how to get running NOW!

First, grab this repo code and build all by yourself on your local beast or ask build automation to do it for you.
Take this dependency with you:

```xml
<dependency>
    <groupId>org.licket</groupId>
    <artifactId>licket-spring-autoconfigure</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

Let's start with simple model first:

```java
public class Contact {

    private String name;
    private String description;
    
    // getters/setters ...
}
    
```

Then a simple Spring service providing some data:

```java
@Service
public class ContactsService {

    public List<Contact> getAllContacts() {
        return newArrayList(
                contact("Chuck Norris", "That's him."),
                contact("Jonh Doe", "Lorem ipsum"),
                contact("Bob Marley", "Je je je")
        );
    }

}
```

As mentioned in preface, Licket derives many concepts from Apache Wicket like logic less HTML templates. You can define view in very similar way:

```html
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

```java
public class ContactsAppRoot extends AbstractLicketMultiContainer<Void> {
    
  @Autowired
  private ContactsPanel contactsPanel;

  public ContactsAppRoot(String id, LicketComponentModelReloader modelReloader) {
      super(id, Void.class, emptyComponentModel(), fromComponentClass(ContactsAppRoot.class), modelReloader);
  }
  
  @Override
  protected void onInitializeContainer() {
    add(contactsPanel);
  }
}

public class ContactsPanel extends AbstractLicketContainer<Contacts> {

    @Autowired
    private ContactsService contactsService;

    public ContactsPanel(String id) {
        super(id, fromComponentContainerClass(ContactsPanel.class));
    }

    @Override
    protected void onInitializeContainer() {
        add(new ContactsList("contact", new LicketComponentModel("contacts"), modelReloader())); 
    }

    private void readContacts() {
        setComponentModel(ofModelObject(fromIterable(contactsService.getAllContacts())));
    }
}

public class ContactsList extends AbstractLicketList<Contact> {

    public ContactsList(String id, LicketComponentModel<String> enclosingComponentPropertyModel,
                        LicketComponentModelReloader modelReloader) {
        super(id, enclosingComponentPropertyModel, Contact.class, modelReloader);

        add(new LicketLabel("name"));
        add(new LicketLabel("description"));
    }
}
```

Next,coin your own Spring Boot configuration class and glue all together:

```java
@Configuration
public class LicketConfiguration {

    @LicketRootContainer
    public ContactsAppRoot root(@Autowired LicketComponentModelReloader modelReloader) {
        return new ContactsAppRoot("contacts-page", modelReloader);
    }

    @LicketComponent
    public ContactsPanel contactsPanel(@Autowired LicketComponentModelReloader modelReloader) {
        return new ContactsPanel("contacts-panel", modelReloader);
    }
    
    @Bean
    public ContactsService contactsService() {
        return new ContactsService();
    }
    
    // other beans ...

}
```

Feel the magic :sparkles:
 
