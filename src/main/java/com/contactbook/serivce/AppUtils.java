package com.contactbook.serivce;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class AppUtils {
    public String getRandomId(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
    public String requestUrlValidation(HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        if (request.getQueryString() != null) {
            requestURL.append("?").append(request.getQueryString());
        }
        String completeURL = requestURL.toString();
        if(completeURL.contains("/api")){
            String[] data= completeURL.split("api/");
            completeURL = data[1];
        }
        return completeURL;
    }
}
