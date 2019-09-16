package id.moxspoy.githubisme.activity;

import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.widget.FrameLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.moxspoy.githubisme.BuildConfig;
import id.moxspoy.githubisme.R;
import id.moxspoy.githubisme.fragment.AboutFragment;
import id.moxspoy.githubisme.fragment.HomeFragment;
import timber.log.Timber;

public class PrimaryActivity extends AppCompatActivity {

    @BindView(R.id.bottom_navigation)
    BottomNavigationView navigation;
    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private HomeFragment homeFragment = new HomeFragment();
    private AboutFragment aboutFragment = new AboutFragment();
    private int currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary);

        ButterKnife.bind(this);
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        setupBottomBar();
    }

    private void setupBottomBar() {

        navigation.setOnNavigationItemSelectedListener(
                menuItem -> {
                    switch (menuItem.getItemId()) {
                        case R.id.nav_home:
                            switchFragments(homeFragment);
                            currentFragment = R.id.nav_home;
                            invalidateOptionsMenu();
                            return true;
                        case R.id.nav_info:
                            switchFragments(aboutFragment);
                            currentFragment = R.id.nav_info;
                            invalidateOptionsMenu();
                            return true;
                        case R.id.nav_exit:
                            showLogoutDialog();
                            return true;
                    }
                    return false;
                }
        );
        navigation.setSelectedItemId(R.id.nav_home);
    }

    private void showLogoutDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(
                new ContextThemeWrapper(this, R.style.Theme_MaterialComponents_Light_Dialog)
        );
        dialog.setMessage("Apakah anda yakin ingin keluar dari akun yang anda miliki?");
        dialog.setTitle("Logout");
        dialog.setPositiveButton("Logout",
                (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                });
        dialog.setNegativeButton("Cancel",
                (dialogInterface, i) -> Timber.d("user cancel logout acount"));
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }

    private void switchFragments(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment curFrag = fragmentManager.getPrimaryNavigationFragment();
        if (curFrag != null) {
            transaction.detach(curFrag);
        }
        String tag = fragment.getClass().getSimpleName();
        Fragment fragment1 = fragmentManager.findFragmentByTag(tag);
        if (fragment1 == null) {
            fragment1 = fragment;
            transaction.add(fragmentContainer.getId(), fragment1, tag);
        } else {
            transaction.attach(fragment1);
        }
        transaction.setPrimaryNavigationFragment(fragment1);
        transaction.setReorderingAllowed(true);
        transaction.commitNowAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        Snackbar snackbar = Snackbar.make
                (fragmentContainer, "Backpressed is not allowed", Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

}
