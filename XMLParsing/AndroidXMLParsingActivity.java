package com.androidhive.xmlparsing;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class AndroidXMLParsingActivity extends ListActivity {

	// All static variables
	static final String URL = "http://172.20.240.11:7002";
	//	static final String URL = "http://api.androidhive.info/pizza/?format=xml";
	// XML node keys
	/*static final String KEY_ITEM = "item"; // parent node
	static final String KEY_ID = "id";
	static final String KEY_NAME = "name";
	static final String KEY_COST = "cost";
	static final String KEY_DESC = "description";*/
	static final String KEY_MATCH = "match"; // parent node
	static final String KEY_HOMETEAM = "home_team";
	static final String KEY_VISITORTEAM = "visitor_team";
	static final String KEY_HOMEGOALS = "home_goals";
	static final String KEY_VISITORGOALS = "visitor_goals";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();

		XMLParser parser = new XMLParser();
		String xml = parser.getXmlFromUrl(URL); // getting XML
		Document doc = parser.getDomElement(xml); // getting DOM element

		NodeList nl = doc.getElementsByTagName(KEY_MATCH);
		// looping through all item nodes <item>
		for (int i = 0; i < nl.getLength(); i++) {
			// creating new HashMap
			HashMap<String, String> map = new HashMap<String, String>();
			Element e = (Element) nl.item(i);
			// adding each child node to HashMap key => value
			map.put(KEY_HOMETEAM, parser.getValue(e, KEY_HOMETEAM));
			map.put(KEY_VISITORTEAM, parser.getValue(e, KEY_VISITORTEAM));
			map.put(KEY_HOMEGOALS,  parser.getValue(e, KEY_HOMEGOALS));
			map.put(KEY_VISITORGOALS, " - " + parser.getValue(e, KEY_VISITORGOALS));

			// adding HashList to ArrayList
			menuItems.add(map);
		}

		// Adding menuItems to ListView
		ListAdapter adapter = new SimpleAdapter(this, menuItems,
				R.layout.list_item,
				new String[] { KEY_HOMETEAM, KEY_VISITORTEAM, KEY_HOMEGOALS, KEY_VISITORGOALS }, new int[] {
						R.id.homeTeam, R.id.visitorTeam, R.id.coalsHome, R.id.coalsVisitor });

		setListAdapter(adapter);

		// selecting single ListView item
		ListView lv = getListView();

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// getting values from selected ListItem
				String homeTeam = ((TextView) view.findViewById(R.id.homeTeam)).getText().toString();
				String visitorTeam = ((TextView) view.findViewById(R.id.visitorTeam)).getText().toString();
				String goals = ((TextView) view.findViewById(R.id.coalsHome)).getText().toString() + ((TextView) view.findViewById(R.id.coalsVisitor)).getText().toString();
				
				// Starting new intent
				Intent in = new Intent(getApplicationContext(), SingleMenuItemActivity.class);
				in.putExtra(KEY_HOMETEAM, homeTeam);
				in.putExtra(KEY_VISITORTEAM, visitorTeam);
				in.putExtra(KEY_HOMEGOALS, goals);
				startActivity(in);

			}
		});
	}
}