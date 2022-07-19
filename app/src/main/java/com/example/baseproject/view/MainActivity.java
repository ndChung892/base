package com.example.baseproject.view;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.app.AppOpsManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.AppOpsManagerCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.baseproject.R;
import com.example.baseproject.adapter.AppAdapter;
import com.example.baseproject.databinding.ActivityMainBinding;
import com.example.baseproject.model.App;
import com.example.baseproject.viewmodel.AppViewModel;

public class MainActivity extends AppCompatActivity {

    private AppViewModel appViewModel;
    private ActivityMainBinding mBinding;
    private AlertDialog mLoadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        appViewModel = new ViewModelProvider(
                this,
                new AppViewModel.MainViewModelFactory(getApplication())).get(AppViewModel.class);
        mBinding.setViewModel(appViewModel);
        mBinding.setLifecycleOwner(this);

        setUpRcv();
        setUpBtnEnableUsageStatsPermission();
//        setUpNotifyWhenLoadingData();
    }

    private void setUpRcv() {
        AppAdapter infoAppAdapter = new AppAdapter(App.diffCallback);

        mBinding.rcvUsages.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mBinding.rcvUsages.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        );
        mBinding.rcvUsages.setAdapter(infoAppAdapter);
        appViewModel.getAppList().observe(this, infoAppAdapter::submitList);
    }

    private void setUpBtnEnableUsageStatsPermission() {
        mBinding.btnGetUsages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
            }
        });
        
    }

    @Override
    protected void onStart() {
        super.onStart();
        actionToGetData();
    }

    private void actionToGetData() {
        if (checkUsageStatsPermission()) {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
            showHideWithPermission();
            if (appViewModel.getAppList().getValue() == null) {
                appViewModel.requestAppList();
            }
        } else {
            showHideNoPermission();
            Toast.makeText(this, "No Permission", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkUsageStatsPermission() {
        int mode = AppOpsManagerCompat.noteOpNoThrow(
                getApplicationContext(),
                AppOpsManager.OPSTR_GET_USAGE_STATS,
                Process.myUid(),
                getPackageName()
        );
        return mode == AppOpsManagerCompat.MODE_ALLOWED;
    }
        @Override
    protected void onDestroy() {
        mBinding = null;
        super.onDestroy();
    }
    private void showHideNoPermission() {
        mBinding.tvRequestPermissionMessage.setVisibility(VISIBLE);
        mBinding.btnGetUsages.setVisibility(VISIBLE);
    }

    private void showHideWithPermission() {
        mBinding.tvRequestPermissionMessage.setVisibility(INVISIBLE);
        mBinding.btnGetUsages.setVisibility(INVISIBLE);
    }


}