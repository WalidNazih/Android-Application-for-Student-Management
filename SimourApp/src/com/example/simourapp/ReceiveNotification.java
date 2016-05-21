package com.example.simourapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class ReceiveNotification extends BroadcastReceiver{
	/*
	protected String title, item;
	
	public ReceiveNotification(String title, String item){
		this.title = title;
		this.item = item;
	}
		*/
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
		builder.setTicker(intent.getStringExtra("title"));
		builder.setContentTitle(intent.getStringExtra("title"));
		builder.setContentText(intent.getStringExtra("item"));
		builder.setSmallIcon(R.drawable.logosmall);
		
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);
		builder.setContentIntent(pendingIntent);
		builder.setDefaults(Notification.DEFAULT_SOUND);
		builder.setAutoCancel(true);
		
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(1,  builder.build());
		
	}

}
