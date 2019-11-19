package com.BlogWebsite.BlogWebsite.controller;


import com.BlogWebsite.BlogWebsite.models.Blog;
import com.BlogWebsite.BlogWebsite.service.UserService;
import com.BlogWebsite.BlogWebsite.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userservice;
    public static Principal principal;


    @GetMapping(path="/all" , produces = "application/json")
    public ArrayList<Users> getAllUsers(Principal principal)
    {
        return userservice.getAllUsers(principal);
    }


    @PostMapping(path = "/signup" , consumes="application/json")
    public boolean signUp(@RequestBody Users user)
    {
        user.setIsActive(1);
        System.out.println("sign up  called");
        System.out.println(user.toString());
        try
        {
            userservice.checkUser(user);
            userservice.add(user);
            return true;
        }
        catch (Exception e)
        {
            System.out.println(e);
            return false;
        }
    }


    @PostMapping(path="/editUser")
    public String editUser(@RequestBody Users user,Principal principal)
    {
        return userservice.editUser(principal,user);
    }


    @PostMapping(path="/deactivateUser")
    public String deactivateUser(@RequestBody Users user)
    {
        System.out.println("deactivating profile of "+principal.getName());
        return userservice.deactivateUser(principal,user);
    }

    @GetMapping(path="/getProfile/{id}" , produces = "application/json")
    public Optional<Users> getProfile(@PathVariable Long id)
    {
        return userservice.getProfile(id);
    }

    @GetMapping(path = "/getMyProfile" , produces = "application/json")
    public Optional<Users> getMyProfile(Principal principal)
    {
        System.out.println("getting my profile");
        return userservice.getMyProfile(principal);
    }
}
