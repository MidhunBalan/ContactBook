package com.contactbook.controller;

import com.contactbook.constants.AppConstants;
import com.contactbook.model.Products;
import com.contactbook.serivce.ProductHelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(AppConstants.VERSION)
public class ProductController {
    @Autowired
    ProductHelperService productHelperService;

    @GetMapping("/getAllProducts")
    public List<Products> getAllProducts() throws ExecutionException, InterruptedException {
        List<Products> contactList = productHelperService.getAllProducts();
        return contactList;
    }

    @GetMapping("/getSuccess")
    public String getSuccessMessage() throws ExecutionException, InterruptedException {
        return "Success data is returned";
    }
}
