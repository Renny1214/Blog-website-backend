package com.BlogWebsite.BlogWebsite.doa;

import com.BlogWebsite.BlogWebsite.models.Blog;
import com.BlogWebsite.BlogWebsite.models.Users;
import org.apache.tomcat.jni.Local;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface BlogRepository extends CrudRepository<Blog,Long> {
    public ArrayList<Blog> findAllByUsersAndPr(Optional<Users> user,boolean pr);
    public void deleteByUsersAndBlogId(Optional<Users> user , Long id);

    public ArrayList<Blog> findAllByUsersAndAccessAndPr(Optional<Users> user, boolean b,boolean pr);

    public ArrayList<Blog> findAllByAccessAndPr(boolean b,boolean pr);

    ArrayList<Blog> findAllByUsers(Optional<Users> user);

    ArrayList<Blog> findAllByUsersAndDateIsBetween(Optional<Users> users, LocalDate date, LocalDate date2);
}
