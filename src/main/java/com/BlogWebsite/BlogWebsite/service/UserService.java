package com.BlogWebsite.BlogWebsite.service;


import com.BlogWebsite.BlogWebsite.doa.UsersRepository;
import com.BlogWebsite.BlogWebsite.models.Blog;
import com.BlogWebsite.BlogWebsite.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    UsersRepository userRepository;

    public void add(Users user)
    {
        userRepository.save(user);
    }

    public Optional<Users> getByEmail(String email)
    {
        System.out.println("getting email");
        return userRepository.findByEmail(email);
    }
    public boolean checkUser(Users user) throws Exception{
        ArrayList<Users> userList = (ArrayList<Users>) userRepository.findAll();
        for(int i=0;i<userList.size();i++)
        {
            if (userList.get(i).getEmail().equals(user.getEmail())
            ) {

                throw new Exception();
            }
            if (userList.get(i).getUsername().equals(user.getUsername())
            ) {

                throw new Exception();
            }
        }
        return true;
    }

    public String editUser(Principal principal, Users user) {
        Optional<Users> users = getByEmail(principal.getName());
        user.setId(users.get().getId());
        user.setIsActive(1);
        userRepository.save(user);
        return "\"user updated successfully\"";
    }

    public String deactivateUser(Principal principal, Users user) {
        Optional<Users> users = getByEmail(principal.getName());
        user.setId(users.get().getId());
        user.setIsActive(0);
        userRepository.save(user);
        return "\"user deactivated successfully\"";
    }


    public Optional<Users> getProfile(Long id) {
        Optional<Users> user  = userRepository.findById(id);
        return user;
    }

    public Optional<Users> getMyProfile(Principal principal) {

        Optional<Users> user = userRepository.findByEmail(principal.getName());
        return user;
    }

    public ArrayList<Users> getAllUsers(Principal principal) {
        Optional<Users> currentUser = userRepository.findByEmail(principal.getName());
        ArrayList<Users> userlist = (ArrayList<Users>) userRepository.findAll();

        userlist.remove(currentUser.get());
        return userlist;
    }

    public void setNewPassword(String email){
        Optional<Users> user=userRepository.findByEmail(email);
        System.out.println("Id of user : "+user.get().getId());
        user.get().setPassword("Scholar123");

        userRepository.save(user.get());
    }
}
