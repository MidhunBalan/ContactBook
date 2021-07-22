package com.contactbook.model;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.*;

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
    private String username;
    private String password;
    private String contactNumber;
    private Map<String, String> address;
}
