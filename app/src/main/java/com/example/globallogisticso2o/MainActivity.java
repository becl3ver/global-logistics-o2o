package com.example.globallogisticso2o;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * 프래그먼트 및 하단 네이게이션 뷰 세팅
 *
 * @author 최재훈
 * @version 1.0, 기본 프래그먼트 화면 전환 구현
 */

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    public MainScreenFragment mainScreenFragment;
    public TermsFragment termsFragment;
    public CommunityFragment communityFragment;
    public AccountFragment accountFragment;

    private BottomNavigationView bottomNavigationView;
    private ActionBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bar = getSupportActionBar();
        bar.setSubtitle("메인 메뉴"); // TODO : 부제목 보이도록 수정
        bar.setLogo(R.drawable.ic_baseline_directions_boat_24);
        bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_USE_LOGO);

        Intent intent = new Intent(this, LoadingScreenActivity.class);
        startActivity(intent);

        mainScreenFragment = new MainScreenFragment();
        termsFragment = new TermsFragment();
        communityFragment = new CommunityFragment();
        accountFragment = new AccountFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, mainScreenFragment).commit();

        bottomNavigationView = findViewById(R.id.select_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.select_main_screen:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, mainScreenFragment).commit();
                        return true;
                    case R.id.select_terms:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, termsFragment).commit();
                        return true;
                    case R.id.select_community:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, communityFragment).commit();
                        return true;
                    case R.id.select_account:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, accountFragment).commit();
                        return true;
                }

                return false;
            }
        });
    }
}