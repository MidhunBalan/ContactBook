package com.contactbook.model;

import com.google.cloud.firestore.annotation.DocumentId;

public class Products {
    @DocumentId
    private String productId;
    private String productName;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
