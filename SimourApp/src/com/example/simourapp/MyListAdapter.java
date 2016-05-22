package com.example.simourapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyListAdapter extends BaseAdapter{

	protected JSONArray results;
	protected Activity activity;
	protected int layout;
	
	private static LayoutInflater inflater = null;
	
	public MyListAdapter(JSONArray results, Activity activity, int layout){
		this.results = results;
		this.activity = activity;
		this.layout = layout;
		inflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.results.length();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Cell cell;
		if(convertView == null){
			convertView = inflater.inflate(layout, null);
			cell = new Cell();
			cell.title = (TextView) convertView.findViewById(R.id.title);
			cell.image = (ImageView) convertView.findViewById(R.id.image);
			convertView.setTag(cell);
		}else{
			cell = (Cell) convertView.getTag();
		}
		
		try {
			JSONObject object = this.results.getJSONObject(position);
			cell.title.setText(object.getString("title"));
			cell.url = object.getString("url");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return convertView;
	}
	
	public String getUrl(int position) throws JSONException{
		JSONObject object = this.results.getJSONObject(position);
		return object.getString("url");
	}
	
	private class Cell{
		protected TextView title;
		protected ImageView image;
		protected String url;
	}

}
