package com.BlogWebsite.BlogWebsite.controller;


import com.BlogWebsite.BlogWebsite.config.ForgetPassowrd;
import com.BlogWebsite.BlogWebsite.service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    UserService userservice;
    private ForgetPassowrd ForgetPasswordConfig;

    @RequestMapping(value="/logout",method = RequestMethod.GET)
    public String logout(HttpServletRequest request , HttpServletResponse response)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth);

        if(auth!=null)
        {
            new SecurityContextLogoutHandler().logout(request,response,auth);
            request.getSession().invalidate();
        }
        return "\"logout successful\"";
    }
    @GetMapping(value="/validateUser" , produces = "application/json")
    public String validateUser(Principal principal)
    {
        System.out.println(principal.getName());
        return "\"user validated\"";
    }

    @PostMapping(path = "/forgetPassword", produces = "application/json")
    public boolean forgetPassword(@RequestBody String email){
        JSONObject jsonObject=new JSONObject(email);

        System.out.println("Sending mail : "+jsonObject.getString("email"));
        try{
            ForgetPasswordConfig.sendMail(jsonObject.getString("email"));
            userservice.setNewPassword(jsonObject.getString("email"));
            return true;
        }
        catch (Exception e){
            System.err.println(e);
            return false;
        }
    }

}
