package com.contactbook.serivce;

import com.contactbook.model.App;
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
public class AppHelperService {
    @Autowired
    FirebaseInitializer db;
    Gson gson = new Gson();

    @Autowired
    AppUtils appUtils;

    public List<App> getAllProducts() throws InterruptedException, ExecutionException {
        List<App> productsList = new ArrayList<>();
        CollectionReference contacts = db.getFirebase().collection("Products");
        ApiFuture<QuerySnapshot> querySnapshot = contacts.get();

        for (DocumentSnapshot doc : querySnapshot.get().getDocuments()) {
            System.out.println(gson.toJson(doc));
            productsList.add(doc.toObject(App.class));
        }
        return productsList;
    }

    public String registerAllProducts() throws InterruptedException, ExecutionException {
        App product1 = new App();
        product1.setName("shopatheaven");
        product1.setKey("e7fc2510-cf3b-4061-9d7a-6a7c70ae51bb");
        saveProduct(product1);

        App product2 = new App();
        product2.setName("contactbook");
        product2.setKey("d9ab07bf-d1b3-4fc4-bf34-c36b8967bdb2");
        saveProduct(product2);
        return "success";
    }

    private void saveProduct(App app) {
        CollectionReference contactRef = db.getFirebase().collection("App");
        contactRef.document(app.getKey()).set(app);
    }

    public String requestedUrl(HttpServletRequest request){
        return appUtils.requestUrlValidation(request);
    }
}
