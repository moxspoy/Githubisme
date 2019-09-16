package id.moxspoy.githubisme.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.robertlevonyan.views.chip.Chip;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.moxspoy.githubisme.Constant;
import id.moxspoy.githubisme.R;
import id.moxspoy.githubisme.activity.SingleUserActivity;
import id.moxspoy.githubisme.model.User;
import id.moxspoy.githubisme.network.GithubService;
import id.moxspoy.githubisme.network.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


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

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this, view);

        initChipListener();

        return view;
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

    private boolean isValidUsername(String username) {
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
        if (!isValidUsername(userName)) {
            Toast.makeText(getContext(), "Ensure username is not empty and only aplhanumeric",
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
                if (response.isSuccessful()) {
                    changeBtnTitle("Search again");
                    User user = response.body();

                    //Oncompleted, passing to Intent
                    Intent intent = new Intent(getContext(), SingleUserActivity.class);
                    intent.putExtra(Constant.INTENT_NAME, user.getName());
                    intent.putExtra(Constant.INTENT_AVATAR_URL, user.getAvatar_url());
                    intent.putExtra(Constant.INTENT_BIO, user.getBio());
                    intent.putExtra(Constant.INTENT_LOGIN, user.getLogin());
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "Github user with name " +
                            userName + " not found", Toast.LENGTH_SHORT).show();
                    changeBtnTitle("Search again");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
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

}
