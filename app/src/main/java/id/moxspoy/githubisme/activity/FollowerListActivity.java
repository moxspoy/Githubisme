package id.moxspoy.githubisme.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.moxspoy.githubisme.Constant;
import id.moxspoy.githubisme.R;
import id.moxspoy.githubisme.adapter.FollowerListAdapter;
import id.moxspoy.githubisme.model.User;
import id.moxspoy.githubisme.network.GithubService;
import id.moxspoy.githubisme.network.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class FollowerListActivity extends AppCompatActivity {

    @BindView(R.id.rv_follower_list)
    RecyclerView rvFollowerList;
    @BindView(R.id.pb_follower_list)
    ProgressBar loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower_list);

        ButterKnife.bind(this);

        String username = getIntent().getStringExtra(Constant.INTENT_NAME);
        requestFollower(username);
    }

    private void requestFollower(String username) {
        Timber.d(username);
        UserService userService = GithubService.createService(UserService.class);
        Call<List<User>> call = userService.getFollowers(username);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                Timber.d(response.raw().toString());
                if (response.isSuccessful()) {
                    dismissLoadingBar();
                    List<User> users = response.body();
                    FollowerListAdapter adapter = new FollowerListAdapter(getApplicationContext(),
                            users);
                    rvFollowerList.setVisibility(View.VISIBLE);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    rvFollowerList.setLayoutManager(layoutManager);
                    rvFollowerList.setAdapter(adapter);
                } else {
                    Toast.makeText(FollowerListActivity.this, "Data not found",
                            Toast.LENGTH_SHORT).show();
                    dismissLoadingBar();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(FollowerListActivity.this, t.getMessage(),
                        Toast.LENGTH_SHORT).show();
                dismissLoadingBar();
            }

        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void dismissLoadingBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (loadingBar.isAnimating()) {
                loadingBar.setVisibility(View.GONE);
            }
        }
    }
}
