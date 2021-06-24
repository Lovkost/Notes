package com.example.notessecondtry.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notessecondtry.R;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    public String[] dataSource;

    public MyAdapter(String[] strings) {
        this.dataSource = strings;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull  MyAdapter.MyViewHolder holder, int position) {
        holder.bind(dataSource[position]);
    }

    @Override
    public int getItemCount() {
        return dataSource.length;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
        public void bind(String s){
            textView.setText(s);
        }
    }
}
