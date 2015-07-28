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
public class Anno extends Activity {
    ListView listitem;
    String comune,XML,tipo_tributo,contribuente;
    static final String KEY_TRIBUTI = "anno"; // parent node
    ArrayList<HashMap<String, String>> anno_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_anno);
        Intent intent = getIntent();
        XML = intent.getStringExtra("XML");
        comune = intent.getStringExtra("comune");
        contribuente = intent.getStringExtra("contribuente");
        tipo_tributo = intent.getStringExtra("tipo_tributo");
        listitem = (ListView) findViewById(R.id.lv_anno);
        XMLParser parser = new XMLParser();
        Document doc = parser.getDomElement(XML); // getting DOM element
        final NodeList contribuente_nodo_t = doc.getElementsByTagName("contribuente");
        anno_list = new ArrayList<HashMap<String, String>>();
        for (int contribuente_prog = 0; contribuente_prog < contribuente_nodo_t.getLength(); contribuente_prog++) {

            Element contribuente_nodo = (Element) contribuente_nodo_t.item(contribuente_prog);
            String contribuente_nodo_rgs = parser.getAttribute(contribuente_nodo,"ragione_sociale").trim();

            if(contribuente_nodo_rgs.equals(contribuente)) {

                NodeList comune_nodo = contribuente_nodo.getChildNodes();

                for(int comune_prog=0;comune_prog<comune_nodo.getLength();comune_prog++){
                    Element comune_val = (Element) comune_nodo.item(comune_prog);
                    String comune_valore = parser.getAttribute(comune_val, "val");
                if (comune_valore.equals(comune)) {

                    NodeList tipo_tributo_nodo = comune_val.getChildNodes();

                    for (int tipo_tributo_prog = 0; tipo_tributo_prog < tipo_tributo_nodo.getLength(); tipo_tributo_prog++) {
                        Element tipo_tributo_val = (Element) tipo_tributo_nodo.item(tipo_tributo_prog);
                        String tipo_tributo_valore = parser.getAttribute(tipo_tributo_val, "val");

                        if (tipo_tributo_valore.equals(tipo_tributo)) {
                            NodeList anno_nodi = tipo_tributo_val.getChildNodes();

                            for (int nodes_anno = 0; nodes_anno < anno_nodi.getLength(); nodes_anno++) {

                                Element anno_element = (Element) anno_nodi.item(nodes_anno);
                                HashMap<String, String> map = new HashMap<String, String>();
                                map.put(KEY_TRIBUTI, parser.getAttribute(anno_element, "val"));
                                anno_list.add(map);
                            }

                        }
                    }

                }
                }
            }

        }
        ListAdapter adapter = new SimpleAdapter(
                Anno.this, anno_list, R.layout.data_anno_riga, new String[]{KEY_TRIBUTI}, new int[]{R.id.Anno});
        // updating listview
        listitem.setAdapter(adapter);

        listitem.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                // on selecting a single album
                // TrackListActivity will be launched to show tracks inside the album
                Intent i = new Intent(Anno.this, Dati.class);
                // send album id to tracklist activity to get list of songs under that album
                String anno = ((TextView) view.findViewById(R.id.Anno)).getText().toString();

                i.putExtra("comune", comune);
                i.putExtra("contribuente", contribuente);
                i.putExtra("tipo_tributo", tipo_tributo);
                i.putExtra("anno",anno);
                i.putExtra("XML", XML);
                startActivity(i);
            }
        });
    }
}
