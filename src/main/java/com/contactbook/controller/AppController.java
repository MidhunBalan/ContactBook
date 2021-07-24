package com.contactbook.controller;

import com.contactbook.constants.AppConstants;
import com.contactbook.model.App;
import com.contactbook.serivce.AppHelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(AppConstants.VERSION)
public class AppController {
    @Autowired
    AppHelperService appHelperService;

    @GetMapping("/getAllProducts")
    public List<App> getAllProducts() throws ExecutionException, InterruptedException {
        List<App> contactList = appHelperService.getAllProducts();
        return contactList;
    }

    @GetMapping("/getSuccess")
    public String getSuccessMessage() throws ExecutionException, InterruptedException {
        return "success";
    }

    @GetMapping("/registerProducts")
    public String registerAllProducts() throws ExecutionException, InterruptedException {
        return appHelperService.registerAllProducts();
    }

    @GetMapping("/getRequestedUrl")
    public String registerRequestedURL(HttpServletRequest request) throws ExecutionException, InterruptedException {
        return appHelperService.requestedUrl(request);
    }
}
