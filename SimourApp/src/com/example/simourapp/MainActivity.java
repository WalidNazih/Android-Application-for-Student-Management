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
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	protected ImageView studentArea;
	protected ImageView research;
	protected Context context;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().hide();
        setContentView(R.layout.activity_main);

        context = this;
        
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
        new Notifier(context).execute(new Connector());
    }
    
    
    class Notifier extends AsyncTask<Connector, String, String>{
    	
    	protected Context context;
    	
    	public Notifier(Context context){
    		this.context = context;
    	}

		@Override
		protected String doInBackground(Connector... params) {
			// TODO Auto-generated method stub
			JSONArray j = params[0].GetAll("http://centipedestudio.co.nf/getNotif.php");
			JSONObject object;
			try {
				object = j.getJSONObject(0);
				if(object != null){
					Intent i = new Intent(context, ReceiveNotification.class);
					i.putExtra("title", object.getString("message"));
					i.putExtra("item", object.getString("item"));
				     AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
				     alarm.set(AlarmManager.RTC_WAKEUP, new GregorianCalendar().getTimeInMillis()+5*1000, PendingIntent.getBroadcast(context, 1, i, PendingIntent.FLAG_UPDATE_CURRENT));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(String... values) {
			 
			
			super.onProgressUpdate(values);
		}
		
		
    	
    }
}
