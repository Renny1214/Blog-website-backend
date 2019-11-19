package com.BlogWebsite.BlogWebsite.service;

import com.BlogWebsite.BlogWebsite.doa.BlogRepository;
import com.BlogWebsite.BlogWebsite.doa.CommentRepository;
import com.BlogWebsite.BlogWebsite.doa.UsersRepository;
import com.BlogWebsite.BlogWebsite.models.Blog;
import com.BlogWebsite.BlogWebsite.models.Comments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

@Service
public class CommentService<UserRepository> {

    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private UsersRepository userRepository;
    @Autowired
    private CommentRepository commentRepository;

    public String addComment(Principal principal, Long blogId, String comment){
        Comments comments=new Comments();

        try{
            comments.setBlog(blogRepository.findById(blogId).get());
            comments.setUser(userRepository.findByEmail(principal.getName()).get());
            comments.setComment(comment);

            commentRepository.save(comments);
            return "Success";
        }
        catch (Exception e){
            System.err.println(e);
            return "Error occurred";
        }
    }

    public ArrayList<Comments> displayComment(Principal principal, Long blogId){
        Optional<Blog> blog=blogRepository.findById(blogId);
        ArrayList<Comments> comments=commentRepository.findAllByBlog(blog);
        Collections.reverse(comments);

        return comments;
    }

    public void deleteComment(Long id){
        commentRepository.deleteById(id);
    }
}
