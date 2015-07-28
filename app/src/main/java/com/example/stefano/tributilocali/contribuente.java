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
 * Created by Avancini.Stefano on 28/07/2015.
 */
public class contribuente extends Activity {
    ListView listitem;
    static final String KEY_TRIBUTI = "contribuente"; // parent node
    static final String KEY_TIPO = "tipo";
    static final String KEY_RGS = "ragione_sociale";
    static final String KEY_CF = "codice_fiscale";
    static final String KEY_MTR = "matricola";
    ArrayList<HashMap<String, String>> albumsList;
    public String XML;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_contribuente);
        Intent intent = getIntent();
        XML = intent.getStringExtra("XML");
        String MTR = intent.getStringExtra("MTR");
        listitem = (ListView) findViewById(R.id.lv_contribuente);
        XMLParser parser = new XMLParser();
        Document doc = parser.getDomElement(XML); // getting DOM element
        NodeList nl = doc.getElementsByTagName(KEY_TRIBUTI);
        albumsList = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < nl.getLength(); i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            Element e = (Element) nl.item(i);
            map.put(KEY_TRIBUTI, parser.getAttribute(e,KEY_RGS)).trim();
            String tipo = parser.getAttribute(e,KEY_TIPO);
            String altri_dati = "";
            if(tipo.equals("D")) {
                altri_dati = "DELEGATO: " + parser.getAttribute(e, KEY_CF).trim() + " - " + parser.getAttribute(e,KEY_MTR);
            }
            else
            {
                altri_dati = parser.getAttribute(e, KEY_CF).trim() + " - " + parser.getAttribute(e,KEY_MTR);
            }
            map.put("ALTRI_DATI", altri_dati);
            if (!albumsList.contains(map)) {
                albumsList.add(map);
            }

        }
        ListAdapter adapter = new SimpleAdapter(
                contribuente.this, albumsList,R.layout.data_contribuente_riga, new String[] { KEY_TRIBUTI,"ALTRI_DATI"}, new int[] {R.id.Contribuente,R.id.Codice_fiscale});
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
                Intent i = new Intent(contribuente.this, Comune.class);
                // send album id to tracklist activity to get list of songs under that album
                String contribuente = ((TextView) view.findViewById(R.id.Contribuente)).getText().toString();
                String item = listitem.getItemAtPosition(position).toString();
                i.putExtra("contribuente", contribuente);
                i.putExtra("XML", XML);
                startActivity(i);
            }
        });
    }
}
