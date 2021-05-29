package com.contactbook.model;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Products {
    @DocumentId
    private String productId;
    private String productName;
}
