package com.androidhive.jsonparsing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SingleMenuItemActivity  extends Activity {
	
	// JSON node keys
	private static final String TAG_NAME = "name";
	private static final String TAG_IP = "ip";
	private static final String TAG_ADDRESS = "address";
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_list_item);
        
        // getting intent data
        Intent in = getIntent();
        
        // Get JSON values from previous intent
        String name = in.getStringExtra(TAG_NAME);
        String ip = in.getStringExtra(TAG_IP);
        String address = in.getStringExtra(TAG_ADDRESS);
        
        // Displaying all values on the screen
        TextView lblName = (TextView) findViewById(R.id.name_label);
        TextView lblCost = (TextView) findViewById(R.id.ip_label);
        TextView lblDesc = (TextView) findViewById(R.id.address_label);
        
        lblName.setText(name);
        lblCost.setText(ip);
        lblDesc.setText(address);
    }
}
