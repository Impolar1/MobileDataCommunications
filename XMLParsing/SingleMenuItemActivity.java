package com.androidhive.xmlparsing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SingleMenuItemActivity  extends Activity {
	
	// XML node keys
	static final String KEY_HOMETEAM = "home_team";
	static final String KEY_VISITORTEAM = "visitor_team";
	static final String KEY_HOMEGOALS = "home_goals";
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_list_item);
        
        // getting intent data
        Intent in = getIntent();
        
        // Get XML values from previous intent
        String homeTeam = in.getStringExtra(KEY_HOMETEAM);
        String visitorTeam = in.getStringExtra(KEY_VISITORTEAM);
        String coals = in.getStringExtra(KEY_HOMEGOALS) ;
        
        // Displaying all values on the screen
        TextView lblHomeTeam = (TextView) findViewById(R.id.homeTeam_label);
        TextView lblVisitorTeam = (TextView) findViewById(R.id.visitorTeam_label);
        TextView lblCoals = (TextView) findViewById(R.id.coals_label);
        
        lblHomeTeam.setText(homeTeam);
        lblVisitorTeam.setText(visitorTeam);
        lblCoals.setText(coals);
    }
}
