package com.contactbook.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.cloud.firestore.annotation.DocumentId;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contact {
    @DocumentId
    private String contactKey;
    private Long createdDate;

    private String firstName;
    private String lastName;
    private String emailId;
    private String encryptedPassword;
    private String contactNumber;
    private Map<String, String> address;
}
