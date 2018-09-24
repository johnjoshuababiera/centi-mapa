package com.usa.centimapa.user;

public interface UserService {
    User findOne(long id);
    User save(User user) throws Exception;
    User updateUser(User user) ;
    User getUser(String idNumber, String password);
    boolean noAdminAccount();

    void initializeAdmin();
}
