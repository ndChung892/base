package com.example.baseproject.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baseproject.Model.App;
import com.example.baseproject.R;

import java.util.List;

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.ViewHolder> {

    List<App> lApp;

    public AppAdapter(List<App> lapp) {
        this.lApp = lapp;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_usages, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        App app = lApp.get(position);
        if (app == null) {
            return;
        }
        holder.txtAppName.setText(app.getNameApp());
        holder.txtAppName.setText(app.getUsageDuration());
        holder.imgIcon.setImageDrawable(app.getIconApp());

    }

    @Override
    public int getItemCount() {
        return lApp.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtAppName, txtAppDuration;
        private ImageView imgIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtAppName = itemView.findViewById(R.id.txtappName);
            txtAppDuration = itemView.findViewById(R.id.txtappDuration);
            imgIcon = itemView.findViewById(R.id.imgIconApp);
        }
    }
}
