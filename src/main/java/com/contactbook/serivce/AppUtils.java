package com.contactbook.serivce;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AppUtils {
    public String getRandomId(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
