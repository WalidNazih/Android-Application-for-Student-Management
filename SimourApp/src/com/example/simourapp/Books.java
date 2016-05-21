package com.example.simourapp;

import java.io.IOException;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class Books extends Activity {

	protected ListView bookList;
	protected Context context;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.activity_books);
		context = this;
		bookList = (ListView) findViewById(R.id.lessonList);
		new GetAllBooks().execute(new Connector());
		bookList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				new Downloader(context, "http://www-us.apache.org/dist/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.tar.gz");
			}
		});
	}

	public void setListAdapter(JSONArray array) {

		this.bookList.setAdapter(new MyListAdapter(array, this, R.layout.booklist));

	}

	private class GetAllBooks extends AsyncTask<Connector, Long, JSONArray> {
		@Override
		protected JSONArray doInBackground(Connector... params) {

			// it is executed on Background thread

			return params[0].GetAll("http://centipedestudio.co.nf/getBooks.php");
		}

		@Override
		protected void onPostExecute(JSONArray jsonArray) {

			setListAdapter(jsonArray);

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.books, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
