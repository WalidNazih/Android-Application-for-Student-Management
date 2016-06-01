package com.example.simourapp;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
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
	protected ImageView msgIcon;
	protected Context context;
	protected SharedPreferences notifications;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.activity_main);

		context = this;
		notifications = getSharedPreferences("Notifications", Context.MODE_PRIVATE);
		
		SharedPreferences.Editor editor = notifications.edit();
		editor.putString("url", "http://192.168.1.10");
		editor.commit();
		
		studentArea = (ImageView) findViewById(R.id.studentarea);
		research = (ImageView) findViewById(R.id.research);
		msgIcon = (ImageView) findViewById(R.id.msg);
	
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
		
		
		new Notifier(msgIcon ,context, notifications).execute(new Connector());

		Intent i = new Intent(context, ReceiveNotification.class);
		AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarm.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 5000, PendingIntent.getBroadcast(context, 1, i, PendingIntent.FLAG_UPDATE_CURRENT));

	}
	
	

}

class Notifier extends AsyncTask<Connector, String, String>{
	
	protected Context context;
	protected SharedPreferences notifications;
	protected ImageView im;
	
	public Notifier(ImageView im, Context context, SharedPreferences notifications){
		this.context = context;
		this.notifications = notifications;
		this.im = im;
	}
	
	@Override
	protected String doInBackground(Connector... params) {
		// TODO Auto-generated method stub
		JSONArray j = params[0].GetAll(notifications.getString("url", "")+":80/getNotif.php");
		JSONObject object;
		
		try {
			object = j.getJSONObject(0);
			if(object != null){
				
				SharedPreferences.Editor editor = notifications.edit();
				String message = notifications.getString("title", "");
				editor.putString("message", object.getString("message"));
				editor.putString("title", object.getString("item"));
				editor.putString("id", object.getString("id"));
				if(object.getString("message").contains("Message") && !object.getString("item").equals(message) && notifications.getString("read", "").equals("yes")){
					editor.putString("read", "no");
				}
				editor.commit();
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		if(notifications.getString("read", "").equals("no")){
			im.setImageResource(R.drawable.newmsg);
			im.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					AlertDialog.Builder dialog = new AlertDialog.Builder(context);
					dialog.setTitle("Message from Mr Simour");
					dialog.setMessage(notifications.getString("title", ""));
					dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							im.setImageResource(R.drawable.read);
							SharedPreferences.Editor editor = notifications.edit();
							editor.putString("read", "yes");
							editor.commit();
						}
					});
					AlertDialog alert = dialog.create();
					alert.show();
				}
			});
		}else{
			im.setImageResource(R.drawable.read);
			im.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					AlertDialog.Builder dialog = new AlertDialog.Builder(context);
					dialog.setTitle("Message from Mr Simour");
					dialog.setMessage(notifications.getString("title", ""));
					dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
					AlertDialog alert = dialog.create();
					alert.show();
				}
			});
		}
	}
	
	
}
