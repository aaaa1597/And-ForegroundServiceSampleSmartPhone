package com.tks.foregroundserviceandlocation;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import static com.tks.foregroundserviceandlocation.Constants.NOTIFICATION_CHANNEL_STARTSTOP;

public class FlcService extends Service {
	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// if user starts the service
		switch (intent.getAction()) {
			case Constants.ACTION.START:
				startForeground(Constants.NOTIFICATION_ID_FOREGROUND_SERVICE, prepareNotification());
				break;
			case Constants.ACTION.STOP:
				stopForeground(true);
				stopSelf();
				break;
			default:
				stopForeground(true);
				stopSelf();
		}

		return START_NOT_STICKY;
	}

	private Notification prepareNotification() {
		/* 通知のチャンネル生成 */
		NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_STARTSTOP, "startstop", NotificationManager.IMPORTANCE_DEFAULT);
		channel.enableVibration(false);
		NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		notificationManager.createNotificationChannel(channel);

		/* 停止ボタン押下の処理実装 */
		Intent stopIntent = new Intent(this, FlcService.class)
								.setAction(Constants.ACTION.STOP);
		PendingIntent pendingStopIntent = PendingIntent.getService(this, 2222, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification);
		remoteViews.setOnClickPendingIntent(R.id.btnStop, pendingStopIntent);

		return new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_STARTSTOP)
				.setContent(remoteViews)
				.setSmallIcon(R.mipmap.ic_launcher)
//				.setCategory(NotificationCompat.CATEGORY_SERVICE)
//				.setOnlyAlertOnce(true)
//				.setOngoing(true)
//				.setAutoCancel(true)
//				.setContentIntent(pendingIntent);
//				.setVisibility(Notification.VISIBILITY_PUBLIC)
				.build();
	}
}
