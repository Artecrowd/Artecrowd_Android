package com.gachon.graduation_project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;

import com.gachon.graduation_project.R;
import com.gachon.graduation_project.adapter.LocationAdapter;
import com.gachon.graduation_project.info.LocationInfo;
import com.gachon.graduation_project.util.BasicFunctions;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ChooseActivity extends BasicFunctions {

    private ArrayList<LocationInfo> locations;
    private RecyclerView recyclerView;
    private LocationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        recyclerView = findViewById(R.id.recycler_location);
        locations = new ArrayList<>(Arrays.asList(
                new LocationInfo("비전타워", new ArrayList<>(Collections.singletonList("비전타워-1층"))),
                new LocationInfo("가천관", new ArrayList<>(Arrays.asList("라곰"))),
                new LocationInfo("AI공학관", new ArrayList<>(Arrays.asList("AI공학관-2층", "AI공학관-4층", "AI공학관-5층", "AI공학관-7층"))),
                new LocationInfo("공과대학1",  new ArrayList<>(Arrays.asList())),
                new LocationInfo("공과대학2", new ArrayList<>(Arrays.asList())),
                new LocationInfo("체육대학", new ArrayList<>(Arrays.asList())),
                new LocationInfo("제 3학생 생활관", new ArrayList<>(Arrays.asList()))
            )
        );
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new LocationAdapter(this, locations);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        Dialog dialog;
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_confirm);
        dialog.show();

        Button btnNo = dialog.findViewById(R.id.btn_no);
        btnNo.setOnClickListener(view -> dialog.dismiss());

        Button btnYes = dialog.findViewById(R.id.btn_yes);
        btnYes.setOnClickListener(view -> {
            dialog.dismiss();
            exit();
        });
    }
}