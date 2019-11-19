package com.BlogWebsite.BlogWebsite.doa;


import com.BlogWebsite.BlogWebsite.models.Blog;
import com.BlogWebsite.BlogWebsite.models.Likes;
import com.BlogWebsite.BlogWebsite.models.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface LikeRepository extends CrudRepository<Likes, Long> {
    public void deleteByUserAndBlog(Optional<Users> user , Optional<Blog> blog);
    ArrayList<Likes> findAllByUser(Optional<Users> user);

    void deleteByBlog(Optional<Blog> blog);
}
