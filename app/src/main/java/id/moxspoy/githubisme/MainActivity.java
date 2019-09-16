package id.moxspoy.githubisme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.robertlevonyan.views.chip.Chip;

import java.util.PrimitiveIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.moxspoy.githubisme.activity.SingleUserActivity;
import id.moxspoy.githubisme.model.User;
import id.moxspoy.githubisme.network.GithubService;
import id.moxspoy.githubisme.network.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.et_username)
    TextInputEditText editTextUserName;
    @BindView(R.id.btn_user)
    MaterialButton btnGetUser;
    @BindView(R.id.chip_moxspoy)
    Chip chipMoxspoy;
    @BindView(R.id.chip_jake)
    Chip chipJake;
    @BindView(R.id.chip_google)
    Chip chipGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        if(BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        initChipListener();
    }

    private void initChipListener() {
        chipMoxspoy.setOnClickListener(view -> {
            setEditText("moxspoy");
        });
        chipJake.setOnClickListener(view -> {
            setEditText("jakewharton");
        });
        chipGoogle.setOnClickListener(view -> {
            setEditText("google");
        });
    }

    private boolean isValidUsername (String username) {
        //check if username is empty
        if (username.isEmpty()) {
            return false;
        }

        //check alphanumeric
        String regex = "^[a-zA-Z0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    @OnClick(R.id.btn_user)
    void getUser() {
        String userName = editTextUserName.getText().toString().toLowerCase();

        //Validate input
        if(!isValidUsername(userName)) {
            Toast.makeText(this, "Ensure username is not empty and only aplhanumeric",
                    Toast.LENGTH_SHORT).show();
            btnGetUser.setText("Try with another username");
            return;
        }

        //Show loading to user
        changeBtnTitle("Loading data...");

        //Request data to github
        UserService userService = GithubService.createService(UserService.class);
        Call<User> call = userService.getUser(userName);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()) {
                    changeBtnTitle("Search again");
                    User user = response.body();

                    //Oncompleted, passing to Intent
                    Intent intent = new Intent(getApplicationContext(), SingleUserActivity.class);
                    intent.putExtra(Constant.INTENT_NAME, user.getName());
                    intent.putExtra(Constant.INTENT_AVATAR_URL, user.getAvatar_url());
                    intent.putExtra(Constant.INTENT_BIO, user.getBio());
                    intent.putExtra(Constant.INTENT_LOGIN, user.getLogin());
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Github user with name " +
                            userName + " not found", Toast.LENGTH_SHORT).show();
                    changeBtnTitle("Search again");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                changeBtnTitle("Search again");
            }

        });

    }

    private void changeBtnTitle(String title) {
        btnGetUser.setText(title);
    }

    private void setEditText(String value) {
        editTextUserName.setText(value);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Timber.d("onStart");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Timber.d("onResume");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Timber.d("onPause");
    }


    @Override
    protected void onStop() {
        super.onStop();
        Timber.d("onStop");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Timber.d("onRestart");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Timber.d("onDestroy");
    }
}
