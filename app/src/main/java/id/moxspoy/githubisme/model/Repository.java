package id.moxspoy.githubisme.model;

import com.google.gson.annotations.SerializedName;

public class Repository {
    private long id;
    private String name;
    private String description;
    private String created_at;
    @SerializedName("private")
    private boolean isPrivate;

    public Repository(long id, String name, String description, String created_at, boolean isPrivate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.created_at = created_at;
        this.isPrivate = isPrivate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }
}
