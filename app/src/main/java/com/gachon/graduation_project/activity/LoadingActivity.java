package com.gachon.graduation_project.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.gachon.graduation_project.R;
import com.gachon.graduation_project.util.Loading;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);    //theme로 지정했다면 삭제한다.

        Loading loading = new Loading(this);
        loading.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loading.dismiss();
                startActivity(new Intent(getApplication(), MainActivity.class));                /* 스플래시 액티비티를 스택에서 제거. */
                LoadingActivity.this.finish();
            }
        }, 2000L);
    }
}