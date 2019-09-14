package id.moxspoy.githubisme.network;

import id.moxspoy.githubisme.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserService {

    @GET("/users/{username}")
    public Call<User> getUser(@Path("username") String username);

}
