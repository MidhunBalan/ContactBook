package com.contactbook.model;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    @DocumentId
    private String accountId;   //PrimaryKey
    private Long createdDate;
    private String firstName;
    private String lastName;
    private String emailId;
    private String companyName;
    private String contactKey;  // ForeignKey
    private String linkedProduct;
    private String roleId;
    private String roleType;
    private List<String> loginType;
    private Map<String, String> address;
    private Map<String, String> additionalInformation;
    private String accountStatus;
}
