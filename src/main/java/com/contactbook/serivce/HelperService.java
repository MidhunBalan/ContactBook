package com.contactbook.serivce;

import com.contactbook.model.Account;
import com.contactbook.model.Contact;
import com.contactbook.model.AuthenticationModel;
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

    @Autowired
    AppUtils appUtils;

    Gson gson = new Gson();

    public boolean userAuthenticate(AuthenticationModel authenticationModel){
        boolean authenticate = false;
        try {
            ApiFuture<QuerySnapshot> future =
                    firebaseDB.getFirebase().collection("Account")
                            .whereEqualTo("emailId", authenticationModel.getEmailId())
                            .whereEqualTo("encryptedPassword", authenticationModel.getEncryptedPassword())
                            .whereEqualTo("linkedProduct", authenticationModel.getProductId())
                            .get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            for (DocumentSnapshot document : documents) {
                authenticate = true;
                System.out.println(document.getId() + " => " + gson.toJson(document.toObject(Account.class)));
            }
        }catch (Exception e){
            System.out.println("Something went wrong"+e);
        }
        return authenticate;
    }
    public Map<String, Object> signupUser(AuthenticationModel authenticationModel){
        System.out.println(gson.toJson(authenticationModel));
        Map<String, Object> response = new HashMap<>();
        try{
            if(!contactAvailabilityCheck(authenticationModel.getEmailId(), authenticationModel.getProductId())){
                Contact savedContact = getSavedContact(authenticationModel);
                System.out.println(gson.toJson("saved contact"));
                if(savedContact.getContactKey() != null) {
                    saveAccountInformation(savedContact, authenticationModel);
                    response.put("success", true);
                    response.put("msg", "user created");
                    response.put("accessToken", "xyzToken");
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

    public boolean contactAvailabilityCheck(String emailId, String productId){
        boolean isPresent = false;
        try{
            ApiFuture<QuerySnapshot> future =
                    firebaseDB.getFirebase().collection("Account")
                            .whereEqualTo("emailId", emailId)
                            .whereEqualTo("linkedProduct", productId)
                            .whereEqualTo("roleType", "OWNER")
                            .get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            for (DocumentSnapshot document : documents) {
                isPresent = true;
                System.out.println(document.getId() + " => " + gson.toJson(document.toObject(Account.class)));
            }
        }catch (Exception e){
            System.out.println("Something went wrong"+e);
        }
        System.out.println("contact availability success");
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

    public List<Account> getAllAccounts() throws InterruptedException, ExecutionException {
        List<Account> accountList = new ArrayList<>();
        CollectionReference contacts = firebaseDB.getFirebase().collection("Account");
        ApiFuture<QuerySnapshot> querySnapshot = contacts.get();

        querySnapshot.get().getDocuments().forEach((doc)-> {
            System.out.println(gson.toJson(doc));
            accountList.add(doc.toObject(Account.class));
        });
        return accountList;
    }
    public Contact getSavedContact(AuthenticationModel authenticationModel){
        Contact contact = null;
        try {
            ApiFuture<QuerySnapshot> future =
                    firebaseDB.getFirebase().collection("Contact")
                            .whereEqualTo("emailId", authenticationModel.getEmailId())
                            .get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            for (DocumentSnapshot document : documents) {
                contact = document.toObject(Contact.class);
                System.out.println(document.getId() + " => " + gson.toJson(document.toObject(Contact.class)));
            }
            if(contact == null){
                contact = Contact.builder()
                        .contactKey(appUtils.getRandomId())
                        .emailId(authenticationModel.getEmailId())
                        .firstName(authenticationModel.getFirstName())
                        .lastName(authenticationModel.getLastName())
                        .contactNumber(authenticationModel.getContactNumber())
                        .address(authenticationModel.getAddress())
                        .createdDate(ZonedDateTime.now().toInstant().toEpochMilli())
                        .encryptedPassword(authenticationModel.getEncryptedPassword())
                        .build();
                saveModel(contact);
                System.out.println("new contact saved");
            }
        }catch(Exception e){
            System.out.println("Something went wrong"+e);
        }
        return contact;
    }

    public Contact saveAccountInformation(Contact contact, AuthenticationModel authenticationModel) {
        try {
            List loginType = new ArrayList<>();
            loginType.add("NORMAL");
            Account account= Account.builder()
                    .accountId(appUtils.getRandomId())
                    .accountStatus("ACTIVE")
                    .createdDate(ZonedDateTime.now().toInstant().toEpochMilli())
                    .firstName(authenticationModel.getFirstName())
                    .lastName(authenticationModel.getLastName())
                    .emailId(contact.getEmailId())
                    .companyName(authenticationModel.getFirstName())
                    .contactKey(contact.getContactKey())
                    .linkedProduct(authenticationModel.getProductId())
                    .roleId(null)
                    .roleType("OWNER")
                    .loginType(loginType)
                    .address(authenticationModel.getAddress())
                    .additionalInformation(new HashMap<>())
                    .accountStatus("ACTIVE")
                    .build();
            saveAccountModel(account);
            return contact;
        }catch(Exception e){
            e.getMessage();
        }
        return null;
    }

    private void saveModel(Contact contact) {
        CollectionReference contactRef = firebaseDB.getFirebase().collection("Contact");
        contactRef.document(contact.getContactKey()).set(contact);
    }
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

    public Map<String, String> getAccountSubscription(Account account){
        Map<String, String> subscription= new HashMap<>();
        subscription.put("accountId", account.getAccountId());
        subscription.put("planName", "FREE");
        subscription.put("planId","4119");
        subscription.put("feature","BASIC");
        return subscription;
    }

    public Account getUserAccountByProductId(String username, String productId) {

        Account account = null;

        try{

            ApiFuture<QuerySnapshot> future =
                    firebaseDB.getFirebase().collection("Account")
                            .whereEqualTo("emailId", username)
                            .whereEqualTo("linkedProduct", productId)
                            .get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            for (DocumentSnapshot document : documents) {
                account = document.toObject(Account.class);
            }

            return account;

        }catch (Exception e){
            System.out.println("Something went wrong"+e);
            return account;
        }
    }
}
