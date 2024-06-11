package com.gachon.graduation_project.activity;

import androidx.annotation.NonNull;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.gachon.graduation_project.R;
import com.gachon.graduation_project.util.BasicFunctions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class AI2Activity extends BasicFunctions {

    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final DatabaseReference databaseReference = firebaseDatabase.getReference();
    private final ArrayList<DatabaseReference> conditionRefList_backward = new ArrayList<>();
    private final ArrayList<DatabaseReference> conditionRefList_forward = new ArrayList<>();
    private final ArrayList<DatabaseReference> conditionRefList_bottom= new ArrayList<>();
    private final ArrayList<ImageView> backwardSeat = new ArrayList<>();
    private final ArrayList<ImageView> forwardSeat = new ArrayList<>();
    private final ArrayList<ImageView> bottomSeat = new ArrayList<>();
    private final DatabaseReference mDatabase = databaseReference.child("AI-2").child("total");
    private TextView textTime;
    private Animation animation;
    private ImageView btnReload;
    private ImageView btnBack;
    private ArrayList<ImageView> congestionBar = new ArrayList<>();
    private TextView textCongestionMain;
    private long nowSeat = 0, congestion = 0;
    private final long allSeat = 34;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai2);

        init();

        animation = AnimationUtils.loadAnimation(this, R.anim.loading);

        textTime = findViewById(R.id.text_time);
        btnReload = findViewById(R.id.btn_reload);
        btnReload.setOnClickListener(onClickListener);
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(onClickListener);
        animation = AnimationUtils.loadAnimation(this, R.anim.loading);
        congestionBar.add(findViewById(R.id.image_blue));
        congestionBar.add(findViewById(R.id.image_green));
        congestionBar.add(findViewById(R.id.image_yellow));
        congestionBar.add(findViewById(R.id.image_orange));
        congestionBar.add(findViewById(R.id.image_red));
        textCongestionMain = findViewById(R.id.text_congestion_main);
//        Button button_test_use = findViewById(R.id.button_test_use);
//        button_test_use.setOnClickListener(onClickListener);
//        Button button_test_unuse = findViewById(R.id.button_test_unuse);
//        button_test_unuse.setOnClickListener(onClickListener);

        checkUse(conditionRefList_backward, backwardSeat);
        checkUse(conditionRefList_forward, forwardSeat);
        checkUse(conditionRefList_bottom, bottomSeat);
        setTotal();
    }

    @SuppressLint("NonConstantResourceId")
    View.OnClickListener onClickListener = (v) -> {
        switch (v.getId()) {
            case R.id.btn_reload:
                btnReload.startAnimation(animation);
                checkUse(conditionRefList_backward, backwardSeat);
                checkUse(conditionRefList_forward, forwardSeat);
                checkUse(conditionRefList_bottom, bottomSeat);
                break;
            case R.id.btn_back:
                finish();
                break;
        }
    };

    public void checkUse(ArrayList<DatabaseReference> conditionRefList, ArrayList<ImageView> seat){
        for(int i=0;i<conditionRefList.size();i++) {
            DatabaseReference conditionRef = conditionRefList.get(i);
            int finalI = i;
            conditionRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    long now = System.currentTimeMillis();
                    Date date = new Date(now);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
                    String getTime = sdf.format(date);
                    textTime.setText("마지막 갱신 시간 : " + getTime);
                    if (Objects.equals(snapshot.getValue(String.class), "1")) {
                        seat.get(finalI).setImageResource(R.drawable.rec_use);
                    } else {
                        seat.get(finalI).setImageResource(R.drawable.rec_unuse);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });
        }
    }

    public void setTotal(){
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nowSeat = snapshot.getValue(Integer.class);
                congestion = (long) ((double)nowSeat / allSeat * 100);
                Log.e("test", nowSeat + "");
                Log.e("test", allSeat + " " + congestion);
                String color = "";
                if(congestion >= 0 && congestion < 20){
                    color = "#5BC0EB";
                    textCongestionMain.setText("매우 원활");
                    setCongestion(0);
                }else if(congestion >= 20 && congestion < 40){
                    color = "#9BC53D";
                    textCongestionMain.setText("원활");
                    setCongestion(1);
                }else if(congestion >=40 && congestion < 60){
                    color = "#FDE74C";
                    textCongestionMain.setText("보통");
                    setCongestion(2);
                }else if(congestion >= 60 && congestion < 80){
                    color = "#FA7921";
                    textCongestionMain.setText("혼잡");
                    setCongestion(3);
                }else{
                    color = "#E55934";
                    textCongestionMain.setText("매우 혼잡");
                    setCongestion(4);
                }
                textCongestionMain.setTextColor(Color.parseColor(color));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void setCongestion(int color){
        for(int i=0;i<congestionBar.size();i++){
            if(i == color)
                congestionBar.get(i).setVisibility(View.VISIBLE);
            else
                congestionBar.get(i).setVisibility(View.GONE);
        }
    }


    public void initDataReference(ArrayList<ImageView> seatList, ArrayList<DatabaseReference> conditionRefList, String name){
        for(int i=0;i<seatList.size();i++){
            DatabaseReference databaseRef = databaseReference.getRef().child("AI-2").child(name + (i+1)).child("use");
            conditionRefList.add(databaseRef);
        }
    }

    public void init(){
        backwardSeat.add(findViewById(R.id.table_backward_1));
        backwardSeat.add(findViewById(R.id.table_backward_2));
        backwardSeat.add(findViewById(R.id.table_backward_3));
        backwardSeat.add(findViewById(R.id.table_backward_4));;

        forwardSeat.add(findViewById(R.id.table_forward_1));
        forwardSeat.add(findViewById(R.id.table_forward_2));
        forwardSeat.add(findViewById(R.id.table_forward_3));
        forwardSeat.add(findViewById(R.id.table_forward_4));

        bottomSeat.add(findViewById(R.id.table_bottom_1));
        bottomSeat.add(findViewById(R.id.table_bottom_2));
        bottomSeat.add(findViewById(R.id.table_bottom_3));

        initDataReference(backwardSeat,  conditionRefList_backward, "backward_");
        initDataReference(forwardSeat, conditionRefList_forward, "forward_");
        initDataReference(bottomSeat, conditionRefList_bottom, "bottom_");
    }
}