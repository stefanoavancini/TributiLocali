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
    String comune,XML,tipo_tributo;
    static final String KEY_TRIBUTI = "anno"; // parent node
    ArrayList<HashMap<String, String>> anno_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_anno);
        Intent intent = getIntent();
        XML = intent.getStringExtra("XML");
        comune = intent.getStringExtra("comune");
        tipo_tributo = intent.getStringExtra("tipo_tributo");
        listitem = (ListView) findViewById(R.id.lv_anno);
        XMLParser parser = new XMLParser();
        Document doc = parser.getDomElement(XML); // getting DOM element
        NodeList nl = doc.getElementsByTagName("comune");
        anno_list = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < nl.getLength(); i++) {

            Element e = (Element) nl.item(i);
            String app = parser.getAttribute(e,"val");
            if(app.equals(comune))
            {
                NodeList fstNmElmntLst = e.getChildNodes();
                int sz=fstNmElmntLst.getLength();
                for(int nodes=0;nodes<sz;nodes++)
                {
                    Element ee = (Element) fstNmElmntLst.item(nodes);
                    String appee = parser.getAttribute(ee,"val");
                    if(appee.equals(tipo_tributo)) {
                        NodeList anno_nodi = ee.getChildNodes();
                        int n_anno_nodi = anno_nodi.getLength();
                        for(int nodes_anno=0;nodes_anno<n_anno_nodi;nodes_anno++)
                        {
                            Element eeee = (Element) anno_nodi.item(nodes_anno);
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put(KEY_TRIBUTI, parser.getAttribute(eeee, "val"));
                            anno_list.add(map);
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
                i.putExtra("tipo_tributo", tipo_tributo);
                i.putExtra("anno",anno);
                i.putExtra("XML", XML);
                startActivity(i);
            }
        });
    }
}
