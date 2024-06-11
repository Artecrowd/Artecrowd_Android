package com.gachon.graduation_project.adapter;

import static com.gachon.graduation_project.info.LocationInfo.*;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gachon.graduation_project.R;
import com.gachon.graduation_project.activity.AI4Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChooseAdapter extends RecyclerView.Adapter<ChooseAdapter.ChooseViewHolder> {
    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final DatabaseReference databaseReference = firebaseDatabase.getReference();
    private ArrayList<LocationDetail> mDataset = new ArrayList<>();
    private final Context context;
    private final Activity activity;

    public static class ChooseViewHolder extends RecyclerView.ViewHolder{
        public RelativeLayout relativeLayout;
        public TextView textCongestion;
        public TextView locationName;

        public ChooseViewHolder(RelativeLayout v){
            super(v);
            relativeLayout = v;
            textCongestion = v.findViewById(R.id.text_congestion);
            locationName = v.findViewById(R.id.text_location);
        }
    }

    public ChooseAdapter(Activity activity, ArrayList<LocationDetail> myDataset){
        this.context = activity.getApplicationContext();
        this.mDataset = myDataset;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ChooseAdapter.ChooseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater
                .from(parent.getContext()).inflate(R.layout.item_choose, parent, false);
        return new ChooseViewHolder(relativeLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull ChooseViewHolder holder, int position) {
        RelativeLayout relativeLayout = holder.relativeLayout;

        String name = mDataset.get(position).getLocation();
        holder.locationName.setText(name);

        String locationId = mDataset.get(position).getId();

        // 비동기 작업을 position과 함께 호출
        setTotal(holder, locationId);

        holder.locationName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String activityName = locationId.replace("-", "") + "Activity";
                String contextName = context.getPackageName();
                System.out.println(activityName);
                try {
                    Class<?> activityClass = Class.forName(contextName + "." + "activity." + activityName);
                    Intent intent = new Intent(activity, activityClass);
                    activity.startActivity(intent);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public long getItemId(int position){
        return mDataset.get(position).hashCode();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setTotal(ChooseViewHolder holder, String locationId) {
        DatabaseReference locationRef = databaseReference.child(locationId);
        locationRef.child("allSeats").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Long allSeat = snapshot.getValue(Long.class);
                if (allSeat != null) {
                    fetchCurrentSeats(holder, locationId, allSeat);
                } else {
                    fetchCurrentSeats(holder, locationId, 0L);
                    Log.e("ChooseAdapter", "Failed to fetch total seats");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ChooseAdapter", "Database error: " + error.getMessage());
            }
        });
    }


    private void fetchCurrentSeats(ChooseViewHolder holder, String locationId, Long allSeat) {
        DatabaseReference seatsRef = databaseReference.child(locationId).child("total");
        seatsRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Integer nowSeat = snapshot.getValue(Integer.class);
                if (nowSeat == null) {
                    seatsRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (task.isComplete()) {
                                Integer result = task.getResult().getValue(Integer.class);
                                if (result != null) {
                                    updateViewHolder(holder, result, allSeat);
                                }else{
                                    updateViewHolder(holder, 0, allSeat);
                                }
                            }
                        }
                    });
                } else {
                    updateViewHolder(holder, nowSeat, allSeat);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ChooseAdapter", "Database error: " + error.getMessage());
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void updateViewHolder(ChooseViewHolder holder, int nowSeat, Long allSeat) {
        int congestion = Math.toIntExact((long) ((double) nowSeat / allSeat * 100));
        String color = "";
        if(congestion >= 0 && congestion < 20){
            color = "#5BC0EB";
        }else if(congestion >= 20 && congestion < 40){
            color = "#9BC53D";
        }else if(congestion >= 40 && congestion < 60){
            color = "#FDE74C";
        }else if(congestion >= 60 && congestion < 80){
            color = "#FA7921";
        }else{
            color = "#E55934";
        }
        holder.textCongestion.setText("(" + nowSeat + "/" + allSeat + ")");
        holder.textCongestion.setTextColor(Color.parseColor(color));
    }
}

