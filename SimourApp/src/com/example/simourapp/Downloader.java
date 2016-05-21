package com.example.simourapp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.ProgressBar;
import android.widget.Toast;


public class Downloader {
	
	protected String url;
	protected int mb = 1024 * 1024;
	protected Context context;
	protected ProgressDialog progress;
	
	public Downloader(Context context, String url){
		this.url = url;
		this.context = context;
		
		new DownloadTask().execute();
	}

	class DownloadTask extends AsyncTask<String, String, Void>{
		
		

		@Override
		protected void onPreExecute() {
			progress = new ProgressDialog(context);
			progress.setIndeterminate(false);
			progress.setMax(100);
			progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progress.setMessage("Downloading file to sdcard/Simour. \nPlease wait ...");
			progress.setCancelable(true);
			progress.setProgress(0);
			progress.show();
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(String... params) {
				
			try {
				URL dUrl = new URL(url);
				HttpURLConnection urlConnection = (HttpURLConnection) dUrl.openConnection();
				urlConnection.setDoOutput(true);
				urlConnection.setRequestMethod("GET");
				urlConnection.connect();
				
				InputStream inputStream = urlConnection.getInputStream();
				
				String fileName = url.split("/")[url.split("/").length - 1];
				
				String ext = Environment.getExternalStorageDirectory().toString();
				
				File folder = new File(ext, "Simour");
				if(!folder.exists())
				folder.mkdir();
				
				File file = new File(folder, fileName);
				file.createNewFile();
				
				FileOutputStream fos = new FileOutputStream(file);
				
				int size = urlConnection.getContentLength();
				
				byte[] buff = new byte[1024];
				int bufferLength = 0;
				int count = 0;
				while((bufferLength = inputStream.read(buff)) > 0){
					count += bufferLength;
					publishProgress(""+((count*100)/size));
					fos.write(buff, 0, bufferLength);
				}
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onProgressUpdate(String... values) {
			//Toast.makeText(context, values[1], Toast.LENGTH_SHORT);
			progress.setProgress(Integer.parseInt(values[0]));
			super.onProgressUpdate(values);
		}



		@Override
		protected void onPostExecute(Void result) {
			Toast.makeText(context,"Download Finished", Toast.LENGTH_LONG).show();
			progress.dismiss();
		}
		
		
	}
	
	

}
