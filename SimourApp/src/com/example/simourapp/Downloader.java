package com.example.simourapp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

public class Downloader {
	
	protected String url;
	protected int mb = 1024 * 1024;
	protected Context context;
	
	public Downloader(Context context, String url){
		this.url = url;
		this.context = context;
		System.out.println("Here");
		new DownloadTask().execute();
	}

	class DownloadTask extends AsyncTask<String, Void, Void>{

		@Override
		protected Void doInBackground(String... params) {
			try {
				
				download();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			Toast.makeText(context, url, Toast.LENGTH_LONG).show();
		}
		
		
		
	}
	

	public void download() throws IOException{
		
		URL dUrl = new URL(url);
		HttpURLConnection urlConnection = (HttpURLConnection) dUrl.openConnection();
		urlConnection.connect();
		System.out.println("Here1");
		InputStream inputStream = urlConnection.getInputStream();
		String fileName = url.split("/")[url.split("/").length - 1];
		//Toast.makeText(context, fileName, Toast.LENGTH_LONG).show();
		String ext = Environment.getExternalStorageDirectory().toString();
		File folder = new File(ext, "Simour");
		folder.mkdir();
		File file = new File(folder, fileName);
		file.createNewFile();
		FileOutputStream fos = new FileOutputStream(file);
		
		int size = urlConnection.getContentLength();
		
		byte[] buff = new byte[mb];
		int bufferLength = 0;
		while((bufferLength = inputStream.read(buff)) > 0){
			fos.write(buff, 0, bufferLength);
		}
		fos.close();
	}

}
