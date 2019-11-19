package com.BlogWebsite.BlogWebsite.controller;


import com.BlogWebsite.BlogWebsite.models.Likes;
import com.BlogWebsite.BlogWebsite.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/like")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @GetMapping(path="/like/{blogId}" , produces = "application/json")
    public Boolean addLike(Principal principal,
                          @PathVariable Long blogId)
    {
        return likeService.addLikes(principal,blogId);
    }

    @GetMapping(path = "/getLikedBlogs",produces = "application/json")
    public ArrayList<Likes> likedBlogs(Principal principal){
        System.out.println("Sending logged in user his liked blogs..");
        return likeService.likedBlogs(principal);
    }

}
