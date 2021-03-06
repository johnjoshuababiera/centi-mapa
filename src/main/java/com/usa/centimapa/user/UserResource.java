package com.usa.centimapa.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserResource {
    
    @Autowired
    private UserService service;

    @PostMapping("/createUser")
    public User create(@RequestBody User user) throws Exception {
        return service.save(user);
    }

    @PostMapping("/updateUser")
    public User update(@RequestBody User user) throws Exception {
        return service.updateUser(user);
    }
    @GetMapping(value="/findById")
    public User findStudent(@RequestParam long id){
        return service.findOne(id);
    }

    @GetMapping(value="/getUser")
    public User getUser(@RequestParam String username, @RequestParam String password){
        return service.getUser(username,password);
    }

}
