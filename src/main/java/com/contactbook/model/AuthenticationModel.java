package com.contactbook.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class AuthenticationModel {
    private String firstName;
    private String lastName;
    private String username;
    private String productId;
    private String password;
    private String contactNumber;
    private Map<String, String> address;
}
