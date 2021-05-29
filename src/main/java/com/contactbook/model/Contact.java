package com.contactbook.model;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Setter
@Builder
@Component
public class Contact {
    @DocumentId
    private String contactKey;
    private Long createdDate;

    private String firstName;
    private String lastName;
    private String emailId;
    private String contactNumber;
    private Map<String, String> address;

}
