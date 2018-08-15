package com.usa.centimapa.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private static BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository repository;

    @Override
    public User findOne(long id) {
        return repository.getOne(id);
    }

    @Override
    public User save(User user) {
        if (!hasDuplicate(user)) {
            encryptPassword(user);
            return repository.save(user);
        }
        return null;
    }

    @Override
    public User updateUser(User user) {
        User savedUser = repository.getOne(user.getId());
        if (!hasDuplicate(user)) {
            savedUser.setUsername(user.getUsername());
            savedUser.setEmail(user.getEmail());
            savedUser.setContact(user.getContact());
        }
        return null;
    }

    public boolean hasDuplicate(User user) {
        User duplicate = repository.findByUsername(user.getUsername());
        if (duplicate(user, duplicate)) {
            return true;
        }
        return false;
    }

    private boolean duplicate(User user, User duplicate) {
        return duplicate != null && duplicate.getId() == user.getId();
    }


    @Override
    public User getUser(String userName, String password) {
        User user = repository.findByUsername(userName);
        if (user != null && PASSWORD_ENCODER.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }


    private void encryptPassword(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }
}
