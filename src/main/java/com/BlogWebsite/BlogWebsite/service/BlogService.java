package com.BlogWebsite.BlogWebsite.service;

import com.BlogWebsite.BlogWebsite.doa.*;
import com.BlogWebsite.BlogWebsite.models.Blog;
import com.BlogWebsite.BlogWebsite.models.Followers;
import com.BlogWebsite.BlogWebsite.models.Users;
import org.hibernate.hql.internal.ast.tree.BinaryLogicOperatorNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.config.authentication.UserServiceBeanDefinitionParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.ReactiveTransaction;
import org.springframework.web.servlet.resource.AbstractResourceResolver;

import javax.transaction.Transactional;
import java.security.Principal;
import java.time.LocalDate;
import java.util.*;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired

    private FollowersRepository followersRepository;

    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private CommentRepository commentRepository;

    public ArrayList<Blog> getRecentBlog() {
        ArrayList<Blog> bloglist = (ArrayList<Blog>) blogRepository.findAllByAccessAndPr(false,true);
        Collections.reverse(bloglist);

        return bloglist;
    }

    public boolean addBlog(Blog blog, Principal principal) {
        ArrayList<Blog> blogs = (ArrayList<Blog>) blogRepository.findAll();
        for (int i = 0; i < blogs.size(); i++) {
            Blog currentBlog = blogs.get(i);

            if (currentBlog.getTitle().equals(blog.getTitle())) {
                return false;
            }
        }

        Optional<Users> user = usersRepository.findByEmail(principal.getName());
        blog.setUsers(user.get());
        blog.setDate(LocalDate.now());
        blog.setVisited(0);
        blog.setPr(true);
        blogRepository.save(blog);
        return true;
    }

    public ArrayList<Blog> getUserBlogs(Principal principal,Long userId) {
        Optional<Users> user = usersRepository.findById(userId);
        ArrayList<Blog> blogs;

        if(principal.getName().equals(user.get().getEmail()))
        {
            blogs = blogRepository.findAllByUsersAndPr(user,true);
        }
        else
        {
            blogs= blogRepository.findAllByUsersAndAccessAndPr(user,false,true);
        }

        return blogs;
    }

    @Transactional
    public boolean deleteBlog(Long id, Principal principal) {
        Optional<Users> user = usersRepository.findByEmail(principal.getName());
        Optional<Blog>blog = blogRepository.findById(id);
        likeRepository.deleteByBlog(blog);
        commentRepository.deleteByBlog(blog);
        blogRepository.deleteByUsersAndBlogId(user, id);

        return true;
    }

    public String upadateBlog(Blog blog,Principal principal) {
      Long id = blog.getBlogId();
      Optional<Blog> oldBlog = blogRepository.findById(id);
      oldBlog.get().setPr(false);
      blogRepository.save(oldBlog.get());

      Optional<Users> user = usersRepository.findByEmail(principal.getName());
      blog.setUsers(user.get());
        blog.setPr(true);
        blog.setLikes(0);
        blog.setBlogId(null);
        blog.setDate(LocalDate.now());
        blogRepository.save(blog);

        return "\"updated blog\"";
    }

    public ArrayList<Blog> searchBlog(String value) {
        ArrayList<Blog> blogs = (ArrayList<Blog>)blogRepository.findAll();
        ArrayList<Blog> result = new ArrayList<>();

        for(int i=0;i<blogs.size();i++)
        {
            if(blogs.get(i).getTitle().contains(value)
            || blogs.get(i).getCategory().equals(value)
            || blogs.get(i).getBlogDescription().equals(value)
            || blogs.get(i).getContent().equals(value)
            || blogs.get(i).getDate().toString().equals(value)
            || blogs.get(i).getUsers().getUsername().equals(value)){
                result.add(blogs.get(i));
            }
        }
        return result;
    }

    public Optional<Blog> getIdInformation(Long blogId) {
        Optional<Blog> blog = blogRepository.findById(blogId);
        blog.get().setVisited(blog.get().getVisited()+1);
        blogRepository.save(blog.get());
        return blogRepository.findById(blogId);
    }

    public ArrayList<Blog> searchCategoryBlog(String value) {
        ArrayList<Blog> blogs = (ArrayList<Blog>) blogRepository.findAll();
        ArrayList<Blog> result = new ArrayList<>();
        for (int i = 0; i < blogs.size(); i++) {
            if (blogs.get(i).getCategory().toLowerCase().equals(value)) {
                result.add(blogs.get(i));
            }
        }
        return result;
    }

    public ArrayList<Blog> findPopular() {
        ArrayList<Blog> blogs= (ArrayList<Blog>)blogRepository.findAll();
        ArrayList<Blog> result = new ArrayList<>();
        for(int i=0;i<blogs.size();i++)
        {
            if(blogs.get(i).getLikes()>5)
            {
                result.add(blogs.get(i));
            }
        }
        return result;
    }


    public ArrayList<Blog> findFollowerBlogs(Principal principal) {
        ArrayList<Blog> list = new ArrayList<>();
        Optional<Users> user = usersRepository.findByEmail(principal.getName());

        ArrayList<Followers> list2 = followersRepository.findAllByUserWhoSentRequestAndRequestAccepted(user,true);
        ArrayList<Users> following = new ArrayList<>();
        for(int i= 0;i<list2.size();i++)
        {
            following.add(list2.get(i).getUserWhoReceiveRequest());
        }
        for(int i=0;i<following.size();i++)
        {
            Optional<Users> user2 = Optional.ofNullable(following.get(i));
            ArrayList<Blog> blogs = blogRepository.findAllByUsersAndDateIsBetween(user2, LocalDate.now().minusDays(2),LocalDate.now());
            list.addAll(blogs);
        }
        Collections.reverse(list);
        return list;
    }

    public ArrayList<String> getCategory(Principal principal) {
        ArrayList<Blog> blogs= getRecentBlog();
        ArrayList<String> category = new ArrayList<>();

        for(int i=0;i<blogs.size();i++)
        {
            if(!category.contains(blogs.get(i).getCategory()))
            {
                category.add(blogs.get(i).getCategory());
            }
        }
        return category;
    }
}
