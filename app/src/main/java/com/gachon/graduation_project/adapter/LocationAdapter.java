package com.gachon.graduation_project.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gachon.graduation_project.R;
import com.gachon.graduation_project.info.LocationInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.units.qual.A;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {

    private ArrayList<LocationInfo> mDataset;
    private final Activity activity;


    public static class LocationViewHolder extends RecyclerView.ViewHolder{
        public RelativeLayout relativeLayout;
        public LocationViewHolder(RelativeLayout v){
            super(v);
            relativeLayout = v;
        }
    }

    public LocationAdapter(Activity activity, ArrayList<LocationInfo> myDataset){
        mDataset = myDataset;
        this.activity = activity;
    }

    @NonNull
    @Override
    public LocationAdapter.LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater
                .from(parent.getContext()).inflate(R.layout.item_location, parent, false);
        return new LocationViewHolder(relativeLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        RelativeLayout relativeLayout = holder.relativeLayout;

        String name = mDataset.get(position).getLocationName();

        TextView locationName = relativeLayout.findViewById(R.id.text_location);
        locationName.setText(name);
        RecyclerView recyclerView = relativeLayout.findViewById(R.id.recycler_choose);

        ArrayList<String> locations = mDataset.get(position).getLocations();
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        ChooseAdapter adapter = new ChooseAdapter(activity, locations);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        View subImage = relativeLayout.findViewById(R.id.line_2);
        ImageView imageArrow = relativeLayout.findViewById(R.id.image_arrow);
        imageArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(recyclerView.getVisibility() == View.VISIBLE){
                    subImage.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                    imageArrow.setImageResource(R.drawable.under_arrow);
                }
                else{
                    subImage.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    imageArrow.setImageResource(R.drawable.top_arrow);
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
}
