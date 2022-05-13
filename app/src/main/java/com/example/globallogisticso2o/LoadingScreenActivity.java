package com.example.globallogisticso2o;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

/**
 * 앱 시작 시 로딩 화면 출력
 *
 * @author 최재훈
 * @version 1.0, 로딩 화면 출력, 사진 설정 안됨
 */

public class LoadingScreenActivity extends AppCompatActivity {
    private static final String TAG = LoadingScreenActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        Log.d(TAG, "로딩 화면");
        startLoadingScreen();
    }

    private void startLoadingScreen() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 2000);
    }
}