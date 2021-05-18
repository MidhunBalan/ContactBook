package com.contactbook.serivce;

import com.contactbook.model.Contact;
import com.contactbook.model.Products;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ProductHelperService {
    @Autowired
    FirebaseInitializer db;
    Gson gson = new Gson();

    public List<Products> getAllProducts() throws InterruptedException, ExecutionException {
        List<Products> productsList = new ArrayList<>();
        CollectionReference contacts = db.getFirebase().collection("Products");
        ApiFuture<QuerySnapshot> querySnapshot = contacts.get();

        for (DocumentSnapshot doc : querySnapshot.get().getDocuments()) {
            System.out.println(gson.toJson(doc));
            productsList.add(doc.toObject(Products.class));
        }
        return productsList;
    }
}
