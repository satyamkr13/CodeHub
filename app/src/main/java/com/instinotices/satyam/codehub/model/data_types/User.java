package com.instinotices.satyam.codehub.model.data_types;

/**
 * This class is used for storing necessary details about a user.
 */
public class User {
    private String login, avatar_url, html_url, name, company, location, email, bio;
    private int id, followers, following;

    public String getLogin() {
        return login;
    }

    public int getId() {
        return id;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public String getName() {
        return name;
    }

    public String getCompany() {
        return company;
    }

    public String getLocation() {
        return location;
    }

    public String getEmail() {
        return email;
    }

    public String getBio() {
        return bio;
    }

    public int getFollowers() {
        return followers;
    }

    public int getFollowing() {
        return following;
    }

}
