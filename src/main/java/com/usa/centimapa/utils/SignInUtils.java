package com.usa.centimapa.utils;

import com.usa.centimapa.user.User;

public class SignInUtils {


    private static SignInUtils INSTANCE = new SignInUtils();

    public static SignInUtils getInstance() {
        return INSTANCE;
    }

    private SignInUtils() {

    }

    private User currentUser;


    public void SignIn(User user){
        currentUser=user;
    }

    public User getCurrentUser(){
        return currentUser;
    }

    public void SignOut(){
        currentUser=null;
    }
}
