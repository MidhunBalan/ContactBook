package com.contactbook.model;

import com.google.cloud.firestore.annotation.DocumentId;

import java.util.List;
import java.util.Map;

public class Account {
    @DocumentId
    private String accountId;
    private Long createdDate;
    private Long lastUpdate;

    private String firstName;
    private String lastName;
    private String emailId;
    private String companyName;
    private Map<String, String> contactInformation;
    private Map<String, String> additionalInformation;
    private String status;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    public Long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Long lastUpdate) {
        this.lastUpdate = lastUpdate;
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

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Map<String, String> getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(Map<String, String> contactInformation) {
        this.contactInformation = contactInformation;
    }

    public Map<String, String> getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(Map<String, String> additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
