package com.contactbook.model;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
public class AccountContactRole {
    @DocumentId
    private String id;
    private List<String> accessRoles;
    private String contactId;
    private String accountId;
}
