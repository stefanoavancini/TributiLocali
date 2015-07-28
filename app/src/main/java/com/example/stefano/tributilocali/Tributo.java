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
public class Tributo extends Activity{
    ListView listitem;
    String comune,XML,contribuente;
    static final String KEY_TRIBUTI = "tipo_tributo"; // parent node
    ArrayList<HashMap<String, String>> tipo_tributo_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_tributo);
        Intent intent = getIntent();
        XML = intent.getStringExtra("XML");
        comune = intent.getStringExtra("comune");
        contribuente = intent.getStringExtra("contribuente");
        listitem = (ListView) findViewById(R.id.lv_tributo);
        XMLParser parser = new XMLParser();
        Document doc = parser.getDomElement(XML); // getting DOM element
        tipo_tributo_list = new ArrayList<HashMap<String, String>>();

        NodeList contribuente_nodo = doc.getElementsByTagName("contribuente");
        for (int contribuente_prg = 0; contribuente_prg < contribuente_nodo.getLength(); contribuente_prg++) {

            Element contribuente_elemento = (Element) contribuente_nodo.item(contribuente_prg);
            String contribuente_valore = parser.getAttribute(contribuente_elemento,"ragione_sociale").trim();

            if(contribuente_valore.equals(contribuente))
            {

                NodeList comune_nodi = contribuente_elemento.getChildNodes();

                for(int comune_prg=0;comune_prg<comune_nodi.getLength();comune_prg++)
                {
                    Element comune_elemento = (Element) comune_nodi.item(comune_prg);
                    String comune_valore = parser.getAttribute(comune_elemento,"val").trim();

                    if(comune_valore.equals(comune))
                    {
                        NodeList tipo_tributo_nodi = comune_elemento.getChildNodes();
                        for(int tipo_tributo_prg=0;tipo_tributo_prg<tipo_tributo_nodi.getLength();tipo_tributo_prg++)
                        {
                            HashMap<String, String> map = new HashMap<String, String>();
                            Element tipo_tributo_elemento = (Element) tipo_tributo_nodi.item(tipo_tributo_prg);
                            map.put(KEY_TRIBUTI, parser.getAttribute(tipo_tributo_elemento, "val"));
                            tipo_tributo_list.add(map);
                        }
                    }

                }
            }

        }

        ListAdapter adapter = new SimpleAdapter(
                Tributo.this, tipo_tributo_list, R.layout.data_tributo_riga, new String[]{KEY_TRIBUTI}, new int[]{R.id.Tributo});
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
                Intent i = new Intent(Tributo.this, Anno.class);
                // send album id to tracklist activity to get list of songs under that album
                String tipo_tributo = ((TextView) view.findViewById(R.id.Tributo)).getText().toString();
                String item = listitem.getItemAtPosition(position).toString();
                i.putExtra("comune", comune);
                i.putExtra("contribuente", contribuente);
                i.putExtra("tipo_tributo", tipo_tributo);
                i.putExtra("XML", XML);
                startActivity(i);
            }
        });
    }
}
