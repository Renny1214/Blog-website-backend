package com.BlogWebsite.BlogWebsite.controller;


import com.BlogWebsite.BlogWebsite.models.Blog;
import com.BlogWebsite.BlogWebsite.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/blog")
public class BlogController {


    @Autowired
    BlogService blogService;
    @GetMapping(path="/recent" , produces = "application/json")
    public ArrayList<Blog> getAllBlog(Principal principal)
    {
        return blogService.getRecentBlog();
    }

    @PostMapping(path ="/createBlog", consumes = "application/json")
    public boolean addBlog(@RequestBody Blog blog , Principal principal)
    {
        System.out.println("added blog");
        return blogService.addBlog(blog,principal);
    }

    @GetMapping(path="/id/{id}")
    public Optional<Blog> getId(
            @PathVariable("id") Long blogId
    )
    {
        return blogService.getIdInformation(blogId);
    }

    @GetMapping(path="/getBlogs/{userId}", produces = "application/json")
    public ArrayList<Blog> getUserBlogs(Principal principal ,
                                        @PathVariable Long userId)
    {
        System.out.println("getting blogs");
        return blogService.getUserBlogs(principal,userId);
    }

    @GetMapping(path = "/deleteBlog/{blogId}" , produces = "application/json")
    public boolean deleteBlog(@PathVariable Long blogId , Principal principal)
    {
        System.out.println("blog deleted");
        return blogService.deleteBlog(blogId,principal);
    }

    @PostMapping(path = "/updateBlog" ,consumes = "application/json")
    public String updateBlog(@RequestBody Blog blog,Principal principal)
    {
        System.out.println("updating blog");
        return blogService.upadateBlog(blog,principal);
    }

    @GetMapping(path="/search/{value}"  , produces ="application/json")
    public ArrayList<Blog> searchBlog(@PathVariable String value)
    {
        return blogService.searchBlog(value);
    }

    @GetMapping(path="/search/category/{value}" , produces = "application/json")
    public ArrayList<Blog> searchCategoryBlog(@PathVariable String value)
    {
        return blogService.searchCategoryBlog(value);
    }

    @GetMapping(path="/popular" ,produces = "application/json")
    public ArrayList<Blog> findPopular()
    {
        return blogService.findPopular();
    }
    @GetMapping(path="/followerBlogs" , produces = "application/json")
    public ArrayList<Blog> findFollowerBlogs(Principal principal)
    {
        return blogService.findFollowerBlogs(principal);
    }


    @GetMapping(path="/getCategory",produces = "application/json")
    public ArrayList<String> getCategory(Principal principal)
    {
        return blogService.getCategory(principal);
    }
}

