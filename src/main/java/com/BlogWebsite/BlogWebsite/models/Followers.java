package com.BlogWebsite.BlogWebsite.models;


import javax.persistence.*;

@Entity
public class Followers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Users userWhoSentRequest;

    @ManyToOne
    private Users userWhoReceiveRequest;

    private boolean requestAccepted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getUserWhoSentRequest() {
        return userWhoSentRequest;
    }

    public void setUserWhoSentRequest(Users userWhoSentRequest) {
        this.userWhoSentRequest = userWhoSentRequest;
    }

    public Users getUserWhoReceiveRequest() {
        return userWhoReceiveRequest;
    }

    public void setUserWhoReceiveRequest(Users userWhoReceiveRequest) {
        this.userWhoReceiveRequest = userWhoReceiveRequest;
    }

    public boolean getRequestAccepted() {
        return requestAccepted;
    }

    public void setRequestAccepted(boolean requestAccepted) {
        this.requestAccepted = requestAccepted;
    }
}
