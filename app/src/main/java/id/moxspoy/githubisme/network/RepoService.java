package id.moxspoy.githubisme.network;

import java.util.List;

import id.moxspoy.githubisme.model.Repository;
import id.moxspoy.githubisme.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RepoService {

    @GET("/users/{username}/repos")
    public Call<List<Repository>> getRepos(@Path("username") String username);

}
