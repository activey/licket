package org.licket.demo.model;

import java.util.ArrayList;
import java.util.List;

public class Contact {

  private String id;
  private String pictureUrl;
  private String name;
  private String description;
  private String content;
  private List<EmailAddress> emails = new ArrayList<>();

  public Contact() {
  }

  public Contact(String id) {
    this.id = id;
  }

  public Contact(String id, String name, String description) {
    this(id);
    this.name = name;
    this.description = description;
  }

  public void addEmail(String id, String emailAddress) {
    emails.add(new EmailAddress(id, emailAddress));
  }

  public void removeEmailById(String id) {
    emails.removeIf(emailAddress -> emailAddress.getId().equals(id));
  }

  public List<EmailAddress> getEmails() {
    return emails;
  }

  public String getPictureUrl() {
    return pictureUrl;
  }

  public void setPictureUrl(String pictureUrl) {
    this.pictureUrl = pictureUrl;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}