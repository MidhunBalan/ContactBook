package com.contactbook.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Setter
public class AuthenticationModel {
    private String firstName;
    private String lastName;
    private String emailId;
    private String productId;
    private String encryptedPassword;
    private String contactNumber;
    private Map<String, String> address;
}
