package com.example.notessecondtry.ui;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notessecondtry.R;
import com.example.notessecondtry.data.Notes;
import com.example.notessecondtry.data.CardSource;
import com.example.notessecondtry.data.OnRegisterMenu;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private final static String TAG = "MyAdapter";
    private CardSource dataSource;
    private OnItemClickListener itemClickListener;
    private final OnRegisterMenu fragment;
    private int menuPosition;


    public int getMenuPosition() {
        return menuPosition;
    }


    public MyAdapter(CardSource strings, OnRegisterMenu fragment) {
        this.dataSource = strings;
        this.fragment = fragment;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        Log.d(TAG, "onCreateViewHolder");
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        holder.setData(dataSource.getCardData(position));
        Log.d(TAG, "onBindViewHolder");
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public void SetOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout constraintLayout;
        private TextView title;
        private TextView description;
        private AppCompatImageView image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
            title = itemView.findViewById(R.id.noteView);
            description = itemView.findViewById(R.id.dataView);
            image = itemView.findViewById(R.id.imageView);
            constraintLayout.setOnClickListener(v -> {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(v, getAdapterPosition());
                }
            });
        }

        public void setData(Notes notes) {
            title.setText(notes.getTitle());
            description.setText(notes.getDescription());
            image.setImageResource(notes.getPicture());
        }

    }
}
