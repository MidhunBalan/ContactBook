package com.contactbook.model;

import com.google.cloud.firestore.annotation.DocumentId;

public class Products {
    @DocumentId
    private String productId;
    private String productName;

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }
}
