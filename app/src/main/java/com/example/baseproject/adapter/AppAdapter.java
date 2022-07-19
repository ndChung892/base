package com.example.baseproject.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baseproject.databinding.ItemUsagesBinding;
import com.example.baseproject.model.App;

import java.util.List;

public class AppAdapter extends ListAdapter<App, AppAdapter.ViewHolder> {

    List<App> lApp;
    public AppAdapter(@NonNull DiffUtil.ItemCallback<App> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemUsagesBinding itemBinding =
                ItemUsagesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        App app = getItem(position);
        if (app != null) {
            holder.mBinding.setApp(app);
            holder.mBinding.executePendingBindings();
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemUsagesBinding mBinding;

        public ViewHolder(@NonNull ItemUsagesBinding binding) {
            super(binding.getRoot());
            mBinding= binding;
        }
    }
}
