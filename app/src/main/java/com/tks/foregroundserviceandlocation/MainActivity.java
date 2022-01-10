package com.tks.foregroundserviceandlocation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		findViewById(R.id.btnStartService).setOnClickListener(view -> {
			Intent intent = new Intent(MainActivity.this, FlcService.class);
			intent.setAction(Constants.ACTION.START);
			startForegroundService(intent);
		});

		findViewById(R.id.btnStopService).setOnClickListener(view -> {
			Intent intent = new Intent(MainActivity.this, FlcService.class);
			intent.setAction(Constants.ACTION.STOP);
			startService(intent);
		});
	}
}