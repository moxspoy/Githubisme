package id.moxspoy.githubisme.network;

import java.util.List;

import id.moxspoy.githubisme.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserService {

    @GET("/users/{username}")
    public Call<User> getUser(@Path("username") String username);

    @GET("/users/{username}/followers")
    public Call<List<User>> getFollowers(@Path("username") String username);

}
