package id.moxspoy.githubisme.model;

import java.io.Serializable;

public class User implements Serializable {
    private long id;
    private String login;
    private String name;
    private String avatar_url;
    private String bio;

    public User(long id, String login, String name, String avatarUrl, String bio) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.avatar_url = avatarUrl;
        this.bio = bio;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

}
