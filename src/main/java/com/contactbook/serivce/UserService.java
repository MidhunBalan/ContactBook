package com.contactbook.serivce;

import com.contactbook.model.AuthenticationModel;
import com.contactbook.model.User;
import com.contactbook.security.JwtTokenProvider;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

import static com.contactbook.constants.SecurityConstants.TOKEN_PREFIX;

@Service
public class UserService {
    @Autowired
    FirebaseInitializer firebaseDB;

    @Autowired
    AppUtils appUtils;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    Gson gson = new Gson();

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    public User saveUser(User newUser){
        User user = User.builder()
                .key(appUtils.getRandomId())
                .username(newUser.getUsername())
                .firstName(newUser.getFirstName())
                .lastName(newUser.getLastName())
                .contactNumber(newUser.getContactNumber())
                .address(newUser.getAddress())
                .createdDate(ZonedDateTime.now().toInstant().toEpochMilli())
                .password(bCryptPasswordEncoder.encode(newUser.getPassword()))
                .build();
            return saveModel(user);

    }
    private User saveModel(User user) {
        CollectionReference userRef = firebaseDB.getFirebase().collection("User");
        userRef.document(user.getKey()).set(user);
        return user;
    }
    User findByUsername(String username){
        User user = null;
        try {
            ApiFuture<QuerySnapshot> future =
                    firebaseDB.getFirebase().collection("User")
                            .whereEqualTo("username", username)
                            .get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            for (DocumentSnapshot document : documents) {
                user = document.toObject(User.class);
                System.out.println(document.getId() + " => " + gson.toJson(user));
            }
        }catch (Exception e){

        }
        return user;
    }

    public User getById(String key){
        User user = null;
        try {
            DocumentReference docRef = firebaseDB.getFirebase().collection("User").document(key);
            ApiFuture<DocumentSnapshot> future = docRef.get();

            DocumentSnapshot document = future.get();
            user = document.toObject(User.class);
        }catch (Exception e){

        }
        return user;

    }

    public String tokenGenerator(AuthenticationModel authenticationModel, boolean signUpFlow, User user) {

        if(!signUpFlow) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationModel.getUsername(),
                            authenticationModel.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = TOKEN_PREFIX +  tokenProvider.generateToken(authentication, false, user);
            return jwt;
        }else{
            String jwt = TOKEN_PREFIX +  tokenProvider.generateToken(null, true, user);
            return jwt;
        }
    }
}
