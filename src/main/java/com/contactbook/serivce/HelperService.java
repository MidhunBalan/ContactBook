package com.contactbook.serivce;

import com.contactbook.enums.LoginType;
import com.contactbook.model.Account;
import com.contactbook.model.Contact;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;
import com.contactbook.constants.AppConstants;


@Service
public class HelperService {
    @Autowired
    FirebaseInitializer firebaseDB;

    Gson gson = new Gson();

    public boolean userAuthenticate(String username, String password){
        boolean authenticate = false;
        try {
            ApiFuture<QuerySnapshot> future =
                    firebaseDB.getFirebase().collection("Contact")
                            .whereEqualTo("userName", username)
                            .whereEqualTo("encryptedPassword", password)
                            .get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            for (DocumentSnapshot document : documents) {
                authenticate = true;
                System.out.println(document.getId() + " => " + gson.toJson(document.toObject(Contact.class)));
            }
        }catch (Exception e){
            System.out.println("Something went wrong"+e);
        }
        return authenticate;
    }
    public Map<String, Object> signupUser(Contact contact){
        Map<String, Object> response = new HashMap<>();
        try{
            if(!contactAvailabilityCheck(contact.getEmailId())){
                Contact savedContact = saveNewContact(contact);
                if(savedContact.getContactKey() != null) {
                    saveAccountInformation(savedContact, AppConstants.CONTACT_BOOK_PRODUCT_KEY);
                    saveAccountInformation(savedContact, AppConstants.SHOP_AT_HEAVEN_PRODUCT_KEY);
                    response.put("success", true);
                    response.put("msg", "user created");
                }
            }else{
                response.put("success", false);
                response.put("msg","user is already available");
            }
        }catch (Exception e){
            System.out.println("something went wrong"+e);
        }

        return response;
    }

    public boolean contactAvailabilityCheck(String emailId){
        boolean isPresent = false;
        try{
            ApiFuture<QuerySnapshot> future =
                    firebaseDB.getFirebase().collection("Contact")
                            .whereEqualTo("emailId", emailId)
                            .get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            for (DocumentSnapshot document : documents) {
                isPresent = true;
                System.out.println(document.getId() + " => " + gson.toJson(document.toObject(Contact.class)));
            }
        }catch (Exception e){
            System.out.println("Something went wrong"+e);
        }
       return isPresent;
    }

    public List<Contact> getContactList() throws InterruptedException, ExecutionException {
        List<Contact> contactList = new ArrayList<>();
        CollectionReference contacts = firebaseDB.getFirebase().collection("Contact");
        ApiFuture<QuerySnapshot> querySnapshot = contacts.get();

        querySnapshot.get().getDocuments().forEach((doc)-> {
            System.out.println(gson.toJson(doc));
            contactList.add(doc.toObject(Contact.class));
        });
        return contactList;
    }
    public Contact saveNewContact(Contact contact) {
        try {
            List<String> products = getAvailableProducts();
            contact.setProducts(products);
            Map<String, String> addInfo = getAdditionalContactInfo();
            contact.setContactInfo(addInfo);
            contact.setCreatedDate(ZonedDateTime.now().toInstant().toEpochMilli());
            List<String> loginType = getLoginType(LoginType.NORMAL.toString());
            contact.setLoginType(loginType);
            saveModel(contact);
            return contact;
        }catch(Exception e){
            e.getMessage();
        }
        return null;
    }
    public Contact saveAccountInformation(Contact contact, String productKey) {
        try {
            Account account= new Account();
            String accountId = contact.getContactKey().concat(productKey);
            account.setAccountId(accountId);
            saveAccountModel(account);
            return contact;
        }catch(Exception e){
            e.getMessage();
        }
        return null;
    }

    //Need to refactor this method to accept all type classes
    private void saveModel(Contact contact) {
        CollectionReference contactRef = firebaseDB.getFirebase().collection("Contact");
        contactRef.document().set(contact);
    }
    //save db with custom id
    private void saveAccountModel(Account account) {
        CollectionReference contactRef = firebaseDB.getFirebase().collection("Account");
        contactRef.document(account.getAccountId()).set(account);
    }

    private List<String> getLoginType(String normal) {
        List<String> loginType = new ArrayList<>();
        loginType.add(normal);
        return loginType;
    }

    private Map<String, String> getAdditionalContactInfo() {
        Map<String, String> addInfo = new HashMap<>();
        addInfo.put("language", "english");
        return addInfo;
    }

    private List<String> getAvailableProducts() {
        List<String> products = new ArrayList<>();
        products.add(AppConstants.CONTACT_BOOK_PRODUCT_KEY);
        products.add(AppConstants.SHOP_AT_HEAVEN_PRODUCT_KEY);
        return products;
    }

    public Contact getContactById(String id){
        Contact contact= null;
        try{
            DocumentReference docRef = firebaseDB.getFirebase().collection("Contact").document(id);
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = future.get();
            if (document.exists()) {
                System.out.println("Document data: " + document.getData());
                contact = gson.fromJson(gson.toJson(document.getData()), Contact.class);
            } else {
                System.out.println("No such document!");
            }
        }catch(Exception e){
            System.out.println("Sorry something went wrong"+e);
        }
        return contact;
    }
}
