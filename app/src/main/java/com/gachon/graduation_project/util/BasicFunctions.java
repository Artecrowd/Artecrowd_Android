package com.gachon.graduation_project.util;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class BasicFunctions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    public void exit(){
        super.onBackPressed();
    }
}
