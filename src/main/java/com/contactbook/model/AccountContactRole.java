package com.contactbook.model;

import com.google.cloud.firestore.annotation.DocumentId;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountContactRole {
    @DocumentId
    private String id;
    private List<String> accessRoles;
    private String contactId;
    private String accountId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getAccessRoles() {
        return accessRoles;
    }

    public void setAccessRoles(List<String> accessRoles) {
        this.accessRoles = accessRoles;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
