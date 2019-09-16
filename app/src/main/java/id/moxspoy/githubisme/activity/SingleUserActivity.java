package id.moxspoy.githubisme.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.button.MaterialButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.moxspoy.githubisme.Constant;
import id.moxspoy.githubisme.R;

public class SingleUserActivity extends AppCompatActivity {

    @BindView(R.id.tv_user)
    TextView tvUserName;
    @BindView(R.id.tv_bio)
    TextView tvBio;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.img_user)
    ImageView imageUser;
    @BindView(R.id.btn_see_follower)
    MaterialButton btnSeeFollower;

    private String userName, bio, login, imageUrlUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_user);

        ButterKnife.bind(this);

        initData();
        initView();
    }

    private void initData() {
        Intent intent = getIntent();
        userName = intent.getStringExtra(Constant.INTENT_NAME);
        bio = intent.getStringExtra(Constant.INTENT_BIO);
        login = intent.getStringExtra(Constant.INTENT_LOGIN);
        imageUrlUser = intent.getStringExtra(Constant.INTENT_AVATAR_URL);
    }

    private void initView() {
        tvUserName.setText(userName);

        tvBio.setText(bio);
        tvLogin.setText(login);

        Glide.with(this).load(imageUrlUser)
                .apply(RequestOptions.circleCropTransform())
                .into(imageUser);
        imageUser.setClickable(true);
    }

    @OnClick(R.id.img_user)
    void toFollowerActivity() {
        Intent intent = new Intent(getApplicationContext(), FollowerListActivity.class);
        intent.putExtra(Constant.INTENT_NAME, login);
        startActivity(intent);
    }

    @OnClick(R.id.btn_see_follower)
    void seeFollower() {
        toFollowerActivity();
    }
}
