package com.airoglobal.airo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.airoglobal.airodemo.R;

public class MainActivity extends Activity {
	// Declare Variables
	ListView listview;
	
	ProgressDialog mProgressDialog;
	ListViewAdapter adapter;
	private List<Numbers> numberlist = null;
	// Set the limit of objects to show
	private int limit = 20;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the view from listview_main.xml
		setContentView(R.layout.listview_main);

		listview = (ListView) findViewById(R.id.listview);
		// Pass the results into ListViewAdapter.java

		// Execute RemoteDataTask AsyncTask
		new RemoteDataTask().execute();
	}

	private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Create a progressdialog
			mProgressDialog = new ProgressDialog(MainActivity.this);
			// Set progressdialog title
			// mProgressDialog.setTitle("Parse.com Load More Tutorial");
			// Set progressdialog message
			mProgressDialog.setMessage("Loading more...");
			mProgressDialog.setIndeterminate(false);
			// Show progressdialog
			mProgressDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// Create the array
			numberlist = new ArrayList<Numbers>();
			try {

				for (int i = 0; i < 20; i++) {
					Numbers map = new Numbers();
					map.setNum("Row " + i);
					numberlist.add(map);
				}

			} catch (Exception e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// Locate the ListView in listview.xml
			listview = (ListView) findViewById(R.id.listview);
			// Pass the results into ListViewAdapter.java
			adapter = new ListViewAdapter(MainActivity.this, numberlist);
			// Binds the Adapter to the ListView
			listview.setAdapter(adapter);
			// Close the progressdialog
			mProgressDialog.dismiss();
			// Create an OnScrollListener
			listview.setOnScrollListener(new OnScrollListener() {

				@Override
				public void onScrollStateChanged(AbsListView view,
						int scrollState) { // TODO Auto-generated method stub
					int threshold = 1;
					int count = listview.getCount();

					if (scrollState == SCROLL_STATE_IDLE) {
						if (listview.getLastVisiblePosition() >= count
								- threshold) {
							// Execute LoadMoreDataTask AsyncTask
							new LoadMoreDataTask().execute();
						}
					}
				}

				@Override
				public void onScroll(AbsListView view, int firstVisibleItem,
						int visibleItemCount, int totalItemCount) {
					// TODO Auto-generated method stub

				}

			});

		}

		private class LoadMoreDataTask extends AsyncTask<Void, Void, Void> {
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				// Create a progressdialog
				mProgressDialog = new ProgressDialog(MainActivity.this);
				// Set progressdialog title
				// mProgressDialog.setTitle("Parse.com Load More Tutorial");
				// Set progressdialog message
				mProgressDialog.setMessage("Loading more...");
				mProgressDialog.setIndeterminate(false);
				// Show progressdialog
				mProgressDialog.show();
			}

			@Override
			protected Void doInBackground(Void... params) {
				// Create the array
				// numberlist = new ArrayList<Numbers>();
				try {

					int tempLimit = limit + 20;
					int i;
					for (i = limit; i < tempLimit; i++) {
						Numbers map = new Numbers();
						map.setNum("Row " + i);
						numberlist.add(map);

					}
					limit = i;
				} catch (Exception e) {
					Log.e("Error", e.getMessage());
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				int position = listview.getLastVisiblePosition();
				// Pass the results into ListViewAdapter.java
				adapter = new ListViewAdapter(MainActivity.this, numberlist);
				// Binds the Adapter to the ListView
				listview.setAdapter(adapter);
				// Show the latest retrived results on the top
				listview.setSelectionFromTop(position, 0);
				// Close the progressdialog
				mProgressDialog.dismiss();
			}
		}

	}

}