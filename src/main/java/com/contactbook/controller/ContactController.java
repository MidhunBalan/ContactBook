package com.contactbook.controller;

import com.contactbook.constants.AppConstants;
import com.contactbook.model.Contact;
import com.contactbook.serivce.HelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(AppConstants.VERSION)
public class ContactController {
    @Autowired
    HelperService helperService;

    @PostMapping("/login")
    public boolean authenticateUser(@RequestBody Map userInfo) throws ExecutionException, InterruptedException {
        return helperService.userAuthenticate(userInfo.get("username").toString(), userInfo.get("password").toString());
    }

    @PostMapping("/signup")
    public Map<String, Object> signupUser(@RequestBody Contact contact) throws ExecutionException, InterruptedException {
        return helperService.signupUser(contact);
    }

    @GetMapping("/getAllContacts")
    public List<Contact> getAllContacts() throws ExecutionException, InterruptedException {
        List<Contact> contactList = helperService.getContactList();
        return contactList;
    }

    @PostMapping("/getContact")
    public Contact getContact(@RequestParam("id") String id) {
        return helperService.getContactById(id);
    }

    @PostMapping("/addContact")
    public String addContact(@RequestBody Contact contact) {
        helperService.saveNewContact(contact);
        return contact.getContactKey();
    }
}
