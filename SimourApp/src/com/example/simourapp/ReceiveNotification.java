package com.example.simourapp;

import java.util.GregorianCalendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class ReceiveNotification extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		new Notifier(context).execute(new Connector());
	}

	class Notifier extends AsyncTask<Connector, String, String> {

		protected Context context;

		public Notifier(Context context) {
			this.context = context;
		}

		@Override
		protected String doInBackground(Connector... params) {
			SharedPreferences pref = context.getSharedPreferences("Notifications", Context.MODE_PRIVATE);
			JSONArray j = params[0].GetAll(pref.getString("url", "")+":80/getNotif.php");
			if (j != null) {

				JSONObject object;
				String id = pref.getString("id", "");
				String message = pref.getString("title", "");
				try {
					object = j.getJSONObject(0);
					if (object != null) {

						if (!id.equals(object.getString("id")) && !message.equals(object.getString("item"))) {
							NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
							builder.setTicker(object.getString("message"));
							builder.setContentTitle(object.getString("message"));
							builder.setContentText(object.getString("item"));
							builder.setSmallIcon(R.drawable.logosmall);

							PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
									new Intent(context, MainActivity.class), 0);
							builder.setContentIntent(pendingIntent);
							builder.setDefaults(Notification.DEFAULT_SOUND);
							builder.setAutoCancel(true);

							NotificationManager notificationManager = (NotificationManager) context
									.getSystemService(Context.NOTIFICATION_SERVICE);
							notificationManager.notify((1+(int)Math.random()*10000), builder.build());

							SharedPreferences.Editor editor = pref.edit();
							editor.putString("message", object.getString("message"));
							editor.putString("title", object.getString("item"));
							editor.putString("id", object.getString("id"));
							editor.commit();

						}

					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return null;
		}
	}
}
