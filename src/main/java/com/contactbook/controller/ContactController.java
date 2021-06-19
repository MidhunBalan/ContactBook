package com.contactbook.controller;

import com.contactbook.constants.AppConstants;
import com.contactbook.model.Account;
import com.contactbook.model.Contact;
import com.contactbook.model.AuthenticationModel;
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

    @PostMapping("/authenticate")
    public boolean authenticateUser(@RequestBody AuthenticationModel authenticationModel) throws ExecutionException, InterruptedException {
        return helperService.userAuthenticate(authenticationModel);
    }

    @PostMapping("/signup")
    public Map<String, Object> signupUser(@RequestBody AuthenticationModel authenticationModel) throws ExecutionException, InterruptedException {
        return helperService.signupUser(authenticationModel);
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

    @GetMapping("/getAllAccounts")
    public List<Account> getAllAccounts() throws ExecutionException, InterruptedException {
        List<Account> contactList = helperService.getAllAccounts();
        return contactList;
    }

    @GetMapping("/getContact/username")
    public Account getContactByUserName(@RequestParam("username") String username, @RequestParam("productId") String productId) {
        return helperService.getUserAccountByProductId(username,productId);
    }
}
