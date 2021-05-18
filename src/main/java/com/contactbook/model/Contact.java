package com.contactbook.model;

import com.google.cloud.firestore.annotation.DocumentId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class Contact {
    @DocumentId
    private String contactKey;
    private Long createdDate;

    private String firstName;
    private String lastName;
    private int age;
    private String emailId;
    private String userName;
    private String contactNumber;
    private String encryptedPassword;
    private List<String> products;
    private Map<String, String> contactInfo;
    private List<String> loginType;

    public String getContactKey() {
        return contactKey;
    }

    public void setContactKey(String contactKey) {
        this.contactKey = contactKey;
    }

    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }

    public Map<String, String> getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(Map<String, String> contactInfo) {
        this.contactInfo = contactInfo;
    }

    public List<String> getLoginType() {
        return loginType;
    }

    public void setLoginType(List<String> loginType) {
        this.loginType = loginType;
    }
}
