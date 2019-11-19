package com.BlogWebsite.BlogWebsite.doa;

import com.BlogWebsite.BlogWebsite.models.Blog;
import com.BlogWebsite.BlogWebsite.models.Comments;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface CommentRepository extends CrudRepository<Comments, Long> {

    public ArrayList<Comments> findAllByBlog(Optional<Blog> blog);
    public void deleteByBlog(Optional<Blog> blog);
}
