package com.BlogWebsite.BlogWebsite.controller;

import com.BlogWebsite.BlogWebsite.doa.BlogRepository;
import com.BlogWebsite.BlogWebsite.doa.CommentRepository;
import com.BlogWebsite.BlogWebsite.doa.UsersRepository;
import com.BlogWebsite.BlogWebsite.models.Blog;
import com.BlogWebsite.BlogWebsite.models.Comments;
import com.BlogWebsite.BlogWebsite.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/comments")
public class CommentsController {


    @Autowired
    private CommentService commentsService;

    @PostMapping(path = "/addComment/Blog/{blogId}", produces = "application/json")
    public String addComment(Principal principal, @RequestBody String comment, @PathVariable Long blogId){
        System.out.println("Adding comment : "+comment);
        return commentsService.addComment(principal,blogId,comment);
    }

    @GetMapping(path = "/displayCommentsOnBlogs/{blogId}", produces = "application/json")
    public ArrayList<Comments> displayBlogComments(Principal principal,@PathVariable Long blogId){
        System.out.println("Displaying comments on : "+blogId);
        return commentsService.displayComment(principal,blogId);
    }

    @GetMapping(path = "/deleteComment/{id}", produces = "application/json")
    public void deleteComment(Principal principal,@PathVariable Long id){
        System.out.println("Deleting comment : "+id+" by : "+principal.getName());
        commentsService.deleteComment(id);
    }


}
