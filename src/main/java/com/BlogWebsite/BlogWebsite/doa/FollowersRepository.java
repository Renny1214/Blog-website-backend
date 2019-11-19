package com.BlogWebsite.BlogWebsite.doa;

import com.BlogWebsite.BlogWebsite.models.Followers;
import com.BlogWebsite.BlogWebsite.models.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface FollowersRepository extends CrudRepository<Followers,Long> {
    public ArrayList<Followers> findAllByUserWhoReceiveRequest(Optional<Users> user);
    public ArrayList<Followers> findAllByUserWhoReceiveRequestAndRequestAccepted(Optional<Users> user, boolean requestAccepted);
    public ArrayList<Followers> findAllByUserWhoSentRequestAndRequestAccepted(Optional<Users> user,boolean requestAccepted);
    public Optional<Followers> findByUserWhoReceiveRequestAndUserWhoSentRequest(Optional<Users> user , Optional<Users> user1);
    public void deleteByUserWhoReceiveRequestAndUserWhoSentRequest(Optional<Users> user,Optional<Users> user1);
}
