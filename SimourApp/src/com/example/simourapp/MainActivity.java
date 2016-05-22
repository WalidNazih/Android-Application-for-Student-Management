package com.example.simourapp;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	protected ImageView studentArea;
	protected ImageView research;
	protected Context context;
	protected SharedPreferences notifications;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.activity_main);

		context = this;
		notifications = getSharedPreferences("Notifications", Context.MODE_PRIVATE);

		studentArea = (ImageView) findViewById(R.id.studentarea);
		research = (ImageView) findViewById(R.id.research);
		
	
		studentArea.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(context, Lessons.class));
			}
		});

		research.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				startActivity(new Intent(context, Research.class));
			}
		});
		
		
		new Notifier(context, notifications).execute(new Connector());

		Intent i = new Intent(context, ReceiveNotification.class);
		AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarm.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 5000, PendingIntent.getBroadcast(context, 1, i, PendingIntent.FLAG_UPDATE_CURRENT));

	}
	
	

}

class Notifier extends AsyncTask<Connector, String, String>{
	
	protected Context context;
	protected SharedPreferences notifications;
	
	public Notifier(Context context, SharedPreferences notifications){
		this.context = context;
		this.notifications = notifications;
	}
	
	@Override
	protected String doInBackground(Connector... params) {
		// TODO Auto-generated method stub
		JSONArray j = params[0].GetAll("http://centipedestudio.co.nf/getNotif.php");
		JSONObject object;
		
		try {
			object = j.getJSONObject(0);
			if(object != null){
				
				SharedPreferences.Editor editor = notifications.edit();
				editor.putString("message", object.getString("message"));
				editor.putString("title", object.getString("item"));
				editor.commit();
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
