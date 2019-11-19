package com.BlogWebsite.BlogWebsite.service;

import com.BlogWebsite.BlogWebsite.controller.FollowersController;
import com.BlogWebsite.BlogWebsite.doa.FollowersRepository;
import com.BlogWebsite.BlogWebsite.doa.UsersRepository;
import com.BlogWebsite.BlogWebsite.models.Followers;
import com.BlogWebsite.BlogWebsite.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;
@Service
public class FollowersService
{
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    FollowersRepository followersRepository;


        public boolean sendRequest(Long id, Principal principal){
            Optional<Users> userWhomRequestIsSent=usersRepository.findById(id);
            Optional<Users> userWhoSentTheRequest=usersRepository.findByEmail(principal.getName());
            ArrayList<Followers> followers=(ArrayList<Followers>) followersRepository
                    .findAllByUserWhoReceiveRequest(userWhomRequestIsSent);

            for(int i=0;i<followers.size();i++){
                if((followers.get(i).getUserWhoReceiveRequest().getUsername().toLowerCase()
                        .equals(userWhomRequestIsSent.get().getUsername().toLowerCase())
                        && (followers.get(i).getUserWhoSentRequest().getUsername().toLowerCase())
                        .equals(userWhoSentTheRequest.get().getUsername().toLowerCase())))
                {
                    return false;
                }
            }

            Followers followersObj=new Followers();
            followersObj.setRequestAccepted(false);
            followersObj.setUserWhoReceiveRequest(userWhomRequestIsSent.get());
            followersObj.setUserWhoSentRequest(userWhoSentTheRequest.get());

            followersRepository.save(followersObj);
            return true;
        }

        public ArrayList<Followers> getAllRequests(Principal principal){
            Optional<Users> user=usersRepository.findByEmail(principal.getName());
            return followersRepository.findAllByUserWhoReceiveRequestAndRequestAccepted(user,false);
        }

        public boolean acceptRequest(Long userWhoSentRequestId,Principal principal){
            Optional<Users> currentUser=usersRepository.findByEmail(principal.getName());
            Optional<Users> userWhoSentRequest=usersRepository.findById(userWhoSentRequestId);

            try{
                Optional<Followers> followers=followersRepository
                        .findByUserWhoReceiveRequestAndUserWhoSentRequest(currentUser,userWhoSentRequest);
                followers.get().setRequestAccepted(true);

                followersRepository.save(followers.get());
            }
            catch (Exception e){
                System.out.println(e);
                return false;
            }

            return true;
        }

        @Transactional
        public boolean declineRequest(Long userWhoSentRequestId,Principal principal){
            Optional<Users> currentUser=usersRepository.findByEmail(principal.getName());
            Optional<Users> userWhoSentRequest=usersRepository.findById(userWhoSentRequestId);

            try{
                followersRepository.deleteByUserWhoReceiveRequestAndUserWhoSentRequest(currentUser,userWhoSentRequest);
                return true;
            }
            catch (Exception e){
                System.out.println(e);
                return false;
            }
        }

        public ArrayList<Followers> getFollowers(Principal principal,Long id){
            Optional<Users> currentUser=usersRepository.findById(id);
            return followersRepository.findAllByUserWhoReceiveRequestAndRequestAccepted(currentUser,true);
        }

        public ArrayList<Followers> getFollowing(Principal principal,Long id){
            Optional<Users> currentUser=usersRepository.findById(id);
            return followersRepository.findAllByUserWhoSentRequestAndRequestAccepted(currentUser,true);
        }

        @Transactional
        public boolean unFollow(Principal principal,Long id){
            Optional<Users> currentUser=usersRepository.findByEmail(principal.getName());
            Optional<Users> userToUnFollow=usersRepository.findById(id);

            try{
                followersRepository.deleteByUserWhoReceiveRequestAndUserWhoSentRequest(userToUnFollow,currentUser);
                return true;
            }
            catch (Exception e){
                System.err.println(e);
                return false;
            }
        }
    }
