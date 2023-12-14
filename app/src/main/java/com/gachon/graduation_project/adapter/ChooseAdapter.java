package com.gachon.graduation_project.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
import androidx.recyclerview.widget.RecyclerView;

import com.gachon.graduation_project.R;
import com.gachon.graduation_project.activity.MainActivity;
import com.gachon.graduation_project.info.LocationInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class ChooseAdapter extends RecyclerView.Adapter<ChooseAdapter.ChooseViewHolder> {

    private ArrayList<String> mDataset = new ArrayList<>();
    private final Activity activity;


    public static class ChooseViewHolder extends RecyclerView.ViewHolder{
        public RelativeLayout relativeLayout;
        public ChooseViewHolder(RelativeLayout v){
            super(v);
            relativeLayout = v;
        }
    }

    public ChooseAdapter(Activity activity, ArrayList<String> myDataset){
        mDataset = myDataset;
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

        String name = mDataset.get(position);

        TextView locationName = relativeLayout.findViewById(R.id.text_location);
        locationName.setText(name);

        locationName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, MainActivity.class);
                activity.startActivity(intent);
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
