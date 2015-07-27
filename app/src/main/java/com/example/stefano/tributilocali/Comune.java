package com.example.stefano.tributilocali;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by stefano on 13/07/2015.
 */
public class Comune extends Activity {
    ListView listitem;
    static final String KEY_TRIBUTI = "comune"; // parent node
    ArrayList<HashMap<String, String>> albumsList;
    public String XML;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_comune);
        Intent intent = getIntent();
        XML = intent.getStringExtra("XML");
        String MTR = intent.getStringExtra("MTR");
        listitem = (ListView) findViewById(R.id.lv_comune);
        XMLParser parser = new XMLParser();
        Document doc = parser.getDomElement(XML); // getting DOM element
        NodeList nl = doc.getElementsByTagName(KEY_TRIBUTI);
        albumsList = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < nl.getLength(); i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            Element e = (Element) nl.item(i);
            map.put(KEY_TRIBUTI, parser.getAttribute(e,"val"));
            if (!albumsList.contains(map)) {
                albumsList.add(map);
            }

        }
        ListAdapter adapter = new SimpleAdapter(
                Comune.this, albumsList,R.layout.data_comune_riga, new String[] { KEY_TRIBUTI}, new int[] {R.id.Comune});
        // updating listview
        listitem.setAdapter(adapter);
        /**
         * Listview item click listener
         * TrackListActivity will be lauched by passing album id
         * */
        listitem.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                // on selecting a single album
                // TrackListActivity will be launched to show tracks inside the album
                Intent i = new Intent(Comune.this, Tributo.class);
                // send album id to tracklist activity to get list of songs under that album
                String comune = ((TextView) view.findViewById(R.id.Comune)).getText().toString();
                String item = listitem.getItemAtPosition(position).toString();
                i.putExtra("comune", comune);
                i.putExtra("XML", XML);
                startActivity(i);
            }
        });
    }
}