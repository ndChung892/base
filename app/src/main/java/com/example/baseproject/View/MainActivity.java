package com.example.baseproject.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.baseproject.R;
import com.example.baseproject.ViewModel.AppViewModel;
import com.example.baseproject.databinding

import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

	private AppViewModel appViewModel;
	private Button btnLoad;
	RecyclerView rcvUsages;
	private ActivityMainBinding
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnLoad = findViewById(R.id.btnGetUsages);
		rcvUsages = findViewById(R.id.rcvUsages);
		appViewModel = new ViewModelProvider(this, new AppViewModel.MainViewModelFactory(getApplication())).get(AppViewModel.class);

		loadStatic();

	}

	private void loadStatic() {
		UsageStatsManager usm = (UsageStatsManager) this.getSystemService(USAGE_STATS_SERVICE);
		List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,  System.currentTimeMillis() - 1000*3600*24,  System.currentTimeMillis());
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			appList = appList.stream().filter(app -> app.getTotalTimeInForeground() > 0).collect(Collectors.toList());
		}
	}
}