package com.androidhive.jsonparsing;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.os.AsyncTask;
import android.widget.ProgressBar;
public class AndroidJSONParsingActivity extends ListActivity {

	
	// JSON Node names
	private static final String TAG_PORTS = "ports";
	private static final String TAG_ID = "id";
	private static final String TAG_NAME = "name";
	private static final String TAG_ADDRESS = "address";
	private static final String TAG_IP = "ip";
	private static final String TAG_PORT = "port";


	// contacts JSONArray
	JSONArray contacts = null;

	ListView lv;

	@TargetApi(Build.VERSION_CODES.CUPCAKE)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		lv = getListView();

		//Launching new screen on selecting single listItem
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				// getting values from selected ListItem
				String name = ((TextView) view.findViewById(R.id.name)).getText().toString();
				String ip = ((TextView) view.findViewById(R.id.ip)).getText().toString();
				String address = ((TextView) view.findViewById(R.id.address)).getText().toString();

				// Starting new intent
				Intent in = new Intent(getApplicationContext(), SingleMenuItemActivity.class);
				in.putExtra(TAG_NAME, name);
				in.putExtra(TAG_IP, ip);
				in.putExtra(TAG_ADDRESS, address);
				startActivity(in);

			}

		});

		// Hashmap for ListView
		ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();

		// Creating JSON Parser instance
		JSONParser jParser = new JSONParser();

		// getting JSON string from URL
		//JSONObject json = jParser.getJSONFromUrl(url);


		
		
		/**
		 * Updating parsed JSON data into ListView
		 * */
		ListAdapter adapter = new SimpleAdapter(this, contactList,
				R.layout.list_item,
				new String[] { TAG_NAME, TAG_IP, TAG_PORT }, new int[] {
						R.id.name, R.id.address, R.id.ip });

		setListAdapter(adapter);


		//starting the task. pass URL to the parameter
	//	String url = "http://api.androidhive.info/contacts/";
		String url = "http://172.20.240.11:7003";
		new ParseTask().execute(url);
	}
	@TargetApi(Build.VERSION_CODES.CUPCAKE)
	private class ParseTask extends AsyncTask<String, Void, ArrayList<HashMap<String, String>>>{
		@Override
		protected void onPreExecute(){
			ProgressBar bar=(ProgressBar)findViewById(R.id.progressBar);
			bar.setVisibility(View.VISIBLE);
		}
		@Override
		protected ArrayList<HashMap<String, String>>doInBackground(String...params){
			String url = params[0];
			//creating json parser instance
			com.androidhive.jsonparsing.JSONParser jsonParser = new com.androidhive.jsonparsing.JSONParser();
			//getting JSON string from URL
			JSONObject json = jsonParser.getJSONFromUrl(url);
			//hashmap from listview
			ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
			try {
				// Getting Array of Contacts
				contacts = json.getJSONArray(TAG_PORTS);

				// looping through All Contacts
				for(int i = 0; i < contacts.length(); i++){
					JSONObject c = contacts.getJSONObject(i);

					// Storing each json item in variable
					String id = c.getString(TAG_ID);
					String name = c.getString(TAG_NAME);
					String ip = c.getString(TAG_IP);
					String address = c.getString(TAG_ADDRESS);
					String gender = c.getString(TAG_PORT);


					HashMap<String, String> map = new HashMap<String, String>();

					// adding each child node to HashMap key => value
					map.put(TAG_ID, id);
					map.put(TAG_NAME, name);
					map.put(TAG_IP, ip);
					map.put(TAG_ADDRESS, address);

					// adding HashList to ArrayList
					contactList.add(map);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return contactList;
		}

		protected void onPostExecute(ArrayList<HashMap<String, String>> contractList){
			ListAdapter adapter = new SimpleAdapter(
					AndroidJSONParsingActivity.this, contractList,
					R.layout.list_item, new String[]{TAG_NAME, TAG_ADDRESS, TAG_IP}, new int[]{
							R.id.name,R.id.address, R.id.ip
			});
			lv.setAdapter(adapter);
			//progressbar invisible
			ProgressBar bar =(ProgressBar)findViewById(R.id.progressBar);
			bar.setVisibility(View.INVISIBLE);
		}
	}
}