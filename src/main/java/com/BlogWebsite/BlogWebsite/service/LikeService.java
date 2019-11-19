package com.BlogWebsite.BlogWebsite.service;


import com.BlogWebsite.BlogWebsite.doa.BlogRepository;
import com.BlogWebsite.BlogWebsite.doa.LikeRepository;
import com.BlogWebsite.BlogWebsite.doa.UsersRepository;
import com.BlogWebsite.BlogWebsite.models.Blog;
import com.BlogWebsite.BlogWebsite.models.Likes;
import com.BlogWebsite.BlogWebsite.models.Users;
import jdk.nashorn.internal.ir.Optimistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class LikeService {
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    BlogRepository blogRepository;
    @Autowired
    LikeRepository likeRepository;

    @Transactional
    public Boolean addLikes(Principal principal, Long blogId) {
        Optional<Users> currentUser = usersRepository.findByEmail(principal.getName());
        Optional<Blog> currentBlog = blogRepository.findById(blogId);

        ArrayList<Likes> likeList = (ArrayList<Likes>) likeRepository.findAll();

        for (int i = 0; i < likeList.size(); i++) {
            if (likeList.get(i).getUser().getUsername().toLowerCase().equals(currentUser.get().getUsername().toLowerCase())
                    && likeList.get(i).getBlog().getBlogId().equals(blogId)) {
                likeRepository.deleteByUserAndBlog(currentUser, currentBlog);
                Blog blog1 = currentBlog.get();
                if (blog1.getLikes() != 0) {
                    blog1.setLikes(blog1.getLikes() - 1);
                    System.out.println("disliking blogs ..");
                    blogRepository.save(blog1);
                }
                return false;
            }
        }

        Likes likes = new Likes();
        likes.setBlog(currentBlog.get());
        likes.setUser(currentUser.get());
        likeRepository.save(likes);

        Blog blogObject = currentBlog.get();
        blogObject.setLikes(blogObject.getLikes() + 1);
        blogRepository.save(blogObject);
        return true;
    }

    public ArrayList<Likes> likedBlogs(Principal principal) {
        Optional<Users> user = usersRepository.findByEmail(principal.getName());
        return likeRepository.findAllByUser(user);
    }
}
