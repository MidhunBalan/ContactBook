package com.contactbook.serivce;

import com.contactbook.model.Account;
import com.contactbook.model.Products;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ProductHelperService {
    @Autowired
    FirebaseInitializer db;
    Gson gson = new Gson();

    @Autowired
    AppUtils appUtils;

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

    public String registerAllProducts() throws InterruptedException, ExecutionException {
        Products product1 = new Products();
        product1.setProductName("shopatheaven");
        product1.setProductId("e7fc2510-cf3b-4061-9d7a-6a7c70ae51bb");
        saveProduct(product1);

        Products product2 = new Products();
        product2.setProductName("contactbook");
        product2.setProductId("d9ab07bf-d1b3-4fc4-bf34-c36b8967bdb2");
        saveProduct(product2);
        return "success";
    }

    private void saveProduct(Products products) {
        CollectionReference contactRef = db.getFirebase().collection("Products");
        contactRef.document(products.getProductId()).set(products);
    }

    public String requestedUrl(HttpServletRequest request){
        return appUtils.requestUrlValidation(request);
    }
}
