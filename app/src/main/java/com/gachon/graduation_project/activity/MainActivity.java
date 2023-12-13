package com.gachon.graduation_project.activity;

import androidx.annotation.NonNull;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.gachon.graduation_project.R;
import com.gachon.graduation_project.util.BasicFunctions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends BasicFunctions {

    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final DatabaseReference databaseReference = firebaseDatabase.getReference();
    private final ArrayList<DatabaseReference> conditionRefList_left = new ArrayList<>();
    private final ArrayList<DatabaseReference> conditionRefList_center_left = new ArrayList<>();
    private final ArrayList<DatabaseReference> conditionRefList_center_right = new ArrayList<>();
    private final ArrayList<DatabaseReference> conditionRefList_right = new ArrayList<>();
    private Button button_reload;
    private final ArrayList<ImageView> centerLeftSeat = new ArrayList<>();
    private final ArrayList<ImageView> centerRightSeat = new ArrayList<>();
    private final ArrayList<ImageView> leftSeat = new ArrayList<>();
    private final ArrayList<ImageView> rightSeat = new ArrayList<>();

    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        animation = AnimationUtils.loadAnimation(this, R.anim.loading);
        button_reload = findViewById(R.id.reload);
        button_reload.setOnClickListener(onClickListener);
        Button button_test_use = findViewById(R.id.button_test_use);
        button_test_use.setOnClickListener(onClickListener);
        Button button_test_unuse = findViewById(R.id.button_test_unuse);
        button_test_unuse.setOnClickListener(onClickListener);

        checkUse(conditionRefList_left, leftSeat);
        checkUse(conditionRefList_center_left, centerLeftSeat);
        checkUse(conditionRefList_center_right, centerRightSeat);
        checkUse(conditionRefList_right, rightSeat);
    }

    @SuppressLint("NonConstantResourceId")
    View.OnClickListener onClickListener = (v) -> {
        switch (v.getId()) {
            case R.id.reload:
                button_reload.startAnimation(animation);
                break;
            case R.id.button_test_use:
                for(DatabaseReference databaseReference1 : conditionRefList_left){
                    databaseReference1.setValue("1");
                }
                for(DatabaseReference databaseReference1 : conditionRefList_center_left){
                    databaseReference1.setValue("1");
                }
                for(DatabaseReference databaseReference1 : conditionRefList_center_right){
                    databaseReference1.setValue("1");
                }
                for(DatabaseReference databaseReference1 : conditionRefList_right){
                    databaseReference1.setValue("1");
                }
                Toast.makeText(this, "success", Toast.LENGTH_SHORT);
                button_reload.clearAnimation();
                animation.setFillEnabled(false);
                break;
            case R.id.button_test_unuse:
                for(DatabaseReference databaseReference1 : conditionRefList_left){
                    databaseReference1.setValue("0");
                }
                for(DatabaseReference databaseReference1 : conditionRefList_center_left){
                    databaseReference1.setValue("0");
                }
                for(DatabaseReference databaseReference1 : conditionRefList_center_right){
                    databaseReference1.setValue("0");
                }
                for(DatabaseReference databaseReference1 : conditionRefList_right){
                    databaseReference1.setValue("0");
                }
                Toast.makeText(this, "success", Toast.LENGTH_SHORT);
                button_reload.clearAnimation();
                animation.setFillEnabled(false);
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
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Button btnYes = dialog.findViewById(R.id.btn_yes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                exit();
            }
        });

    }

    public void initDataReference(ArrayList<ImageView> seatList, ArrayList<DatabaseReference> conditionRefList, String name){
        for(int i=0;i<seatList.size();i++){
            DatabaseReference databaseRef = databaseReference.getRef().child("AI-4").child(name + (i+1)).child("use");
            conditionRefList.add(databaseRef);
        }
    }

    public void init(){
        leftSeat.add(findViewById(R.id.left_1));
        leftSeat.add(findViewById(R.id.left_2));
        leftSeat.add(findViewById(R.id.left_3));
        leftSeat.add(findViewById(R.id.left_4));
        leftSeat.add(findViewById(R.id.left_5));
        leftSeat.add(findViewById(R.id.left_6));
        leftSeat.add(findViewById(R.id.left_7));
        leftSeat.add(findViewById(R.id.left_8));

        centerLeftSeat.add(findViewById(R.id.center_left_1));
        centerLeftSeat.add(findViewById(R.id.center_left_2));
        centerLeftSeat.add(findViewById(R.id.center_left_3));
        centerLeftSeat.add(findViewById(R.id.center_left_4));
        centerLeftSeat.add(findViewById(R.id.center_left_5));
        centerLeftSeat.add(findViewById(R.id.center_left_6));
        centerLeftSeat.add(findViewById(R.id.center_left_7));

        centerRightSeat.add(findViewById(R.id.center_right_1));
        centerRightSeat.add(findViewById(R.id.center_right_2));
        centerRightSeat.add(findViewById(R.id.center_right_3));
        centerRightSeat.add(findViewById(R.id.center_right_4));
        centerRightSeat.add(findViewById(R.id.center_right_5));
        centerRightSeat.add(findViewById(R.id.center_right_6));
        centerRightSeat.add(findViewById(R.id.center_right_7));

        rightSeat.add(findViewById(R.id.right_1));
        rightSeat.add(findViewById(R.id.right_2));
        rightSeat.add(findViewById(R.id.right_3));
        rightSeat.add(findViewById(R.id.right_4));
        rightSeat.add(findViewById(R.id.right_5));
        rightSeat.add(findViewById(R.id.right_6));
        rightSeat.add(findViewById(R.id.right_7));
        rightSeat.add(findViewById(R.id.right_8));
        rightSeat.add(findViewById(R.id.right_9));
        rightSeat.add(findViewById(R.id.right_10));
        rightSeat.add(findViewById(R.id.right_11));
        rightSeat.add(findViewById(R.id.right_12));

        initDataReference(leftSeat,  conditionRefList_left, "left_");
        initDataReference(centerLeftSeat, conditionRefList_center_left, "center_left_");
        initDataReference(centerRightSeat, conditionRefList_center_right, "center_right_");
        initDataReference(rightSeat, conditionRefList_right, "right_");
    }
}