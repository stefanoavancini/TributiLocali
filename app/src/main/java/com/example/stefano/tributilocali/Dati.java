package com.example.stefano.tributilocali;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Created by stefano on 16/07/2015.
 */
public class Dati extends Activity{
    ListView listitem;
    String comune,XML,tipo_tributo,anno, contribuente;
    TextView anagrafica;
    static final String KEY_TRIBUTI = "dati"; // parent node
    static final String KEY_TIPO = "TIPO";
    static final String KEY_ANNO = "ANNO";
    static final String KEY_CATEGORIA = "CATEGORIA";
    static final String KEY_VIA = "VIA";
    static final String KEY_CIVICO = "CIVICO";
    static final String KEY_BARRATO = "BARRATO";
    static final String KEY_SEZIONE = "SEZIONE";
    static final String KEY_PED = "PED";
    static final String KEY_PED_BARRATO = "PED_BARRATO";
    static final String KEY_SUBALTERNO = "SUBALTERNO";
    static final String KEY_RENDITA = "RENDITA";
    static final String KEY_QUOTA = "QUOTA";
    static final String KEY_MESI = "MESI";
    static final String KEY_ABIT_PRINC = "ABIT_PRINC";
    static final String KEY_AGEV_PART = "AGEV_PART";
    static final String KEY_ALIQUOTA = "ALIQUOTA";
    static final String KEY_ACCONTO = "ACCONTO";
    static final String KEY_SALDO = "SALDO";
    static final String KEY_DETRAZIONE = "DETRAZIONE";
    static final String KEY_TOTALE = "TOTALE";
    static final String KEY_ENTE = "ENTE";
    static final String KEY_SEZIONE_PERT = "SEZIONE_PERT";
    static final String KEY_PED_PERT = "PED_PERT";
    static final String KEY_BARRATO_PERT = "BARRATO_PERT";
    static final String KEY_SUB_PERT = "SUB_PERT";
    static final String KEY_ACCONTO_TASI = "ACCONTO_TASI";
    static final String KEY_SALDO_TASI = "SALDO_TASI";
    static final String KEY_ALIQUOTA_TASI = "ALIQUOTA_TASI";
    static final String KEY_TOTALE_TASI = "TOTLAE_TASI";
    static final String KEY_SUPERFICIE = "SUPERFICIE";
    static final String KEY_DATA_INIZIO = "DATA_INIZIO";
    static final String KEY_DATA_FINE = "DATA_FINE";
    static final String KEY_COMPONENTI = "COMPONENTI";
    static final String KEY_PARTE_FISSA = "PARTE_FISSA";
    static final String KEY_PARTE_VARIABILE = "PARTE_VARIABILE";
    static final String KEY_MAGGIORAZIONE = "MAGGIORAZIONE";
    static final String KEY_RIDUZIONE_1 = "RIDUZIONE_1";
    static final String KEY_RIDUZIONE_2 = "RIDUZIONE_2";
    static final String KEY_RIDUZIONE_3 = "RIDUZIONE_3";
    static final String KEY_RAGIONE_SOCIALE = "RAGIONE_SOCIALE";
    static final String KEY_CODICE_FISCALE = "CODICE_FISCALE";
    static final String KEY_MATRICOLA = "MATRICOLA";

    ArrayList<HashMap<String, String>> dati_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_finale);
        Intent intent = getIntent();
        XML = intent.getStringExtra("XML");
        comune = intent.getStringExtra("comune");
        contribuente = intent.getStringExtra("contribuente");
        tipo_tributo = intent.getStringExtra("tipo_tributo");
        anno = intent.getStringExtra("anno");
        listitem = (ListView) findViewById(R.id.lv_dati);
        XMLParser parser = new XMLParser();
        Document doc = parser.getDomElement(XML); // getting DOM element
        dati_list = new ArrayList<HashMap<String, String>>();

        final NodeList contribuente_nodo_t = doc.getElementsByTagName("contribuente");
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
                                    String anno_value = parser.getAttribute(anno_element,"val");

                                    if(anno_value.equals(anno))
                                    {
                                        NodeList dati_nodi = anno_element.getChildNodes();
                                        int n_dati_nodi = dati_nodi.getLength();
                                        for(int nodes_dati=0;nodes_dati<n_dati_nodi;nodes_dati++)
                                        {
                                            Element eeee = (Element) dati_nodi.item(nodes_dati);
                                            if (eeee.getTagName().equals("dati"))
                                            {
                                            HashMap<String, String> map = new HashMap<String, String>();
                                            String categoria = parser.getValue(eeee,KEY_CATEGORIA) + " Rendita " + parser.getValue(eeee,KEY_RENDITA);
                                            map.put(KEY_CATEGORIA, categoria);
                                            String dati_catastali = "C.C. " + parser.getValue(eeee, KEY_SEZIONE) + "P.Ed. " + parser.getValue(eeee,KEY_PED);
                                            if(!parser.getValue(eeee,KEY_PED_BARRATO).equals("0")){
                                                dati_catastali += "/" + parser.getValue(eeee,KEY_PED_BARRATO);
                                            }
                                            dati_catastali += "Sub. " + parser.getValue(eeee,KEY_SUBALTERNO);
                                            String via = parser.getValue(eeee,KEY_VIA).trim() + " " + parser.getValue(eeee,KEY_CIVICO) + " " + parser.getValue(eeee,KEY_BARRATO);
                                                map.put(KEY_VIA, via);
                                                map.put(KEY_SEZIONE, dati_catastali);
                                                String quota = parser.getValue(eeee,KEY_QUOTA) + " Mesi: " + parser.getValue(eeee,KEY_MESI);
                                                map.put(KEY_QUOTA, quota);
                                                map.put(KEY_ABIT_PRINC, "Abitazione principale: " + parser.getValue(eeee, KEY_ABIT_PRINC));
                                                String importo = parser.getValue(eeee, KEY_TOTALE);
                                                //importo = "1234.568";
                                                Double impdbl = Double.parseDouble(importo);
                                                map.put(KEY_TOTALE, "€ " + String.format(Locale.GERMANY,"%.2f", impdbl));


                                                dati_list.add(map);
                                            }
                                        }
                                    }

                                }

                            }
                        }

                    }
                }
            }

        }



        ListAdapter adapter = new SimpleAdapter(
                Dati.this, dati_list, R.layout.data_finale_riga, new String[]{KEY_VIA,KEY_SEZIONE,KEY_CATEGORIA,KEY_QUOTA,KEY_ABIT_PRINC,KEY_TOTALE}, new int[]{R.id.via,R.id.dati_catastali,R.id.categoria,R.id.quota,R.id.abit_princ,R.id.importo});
        // updating listview
        listitem.setAdapter(adapter);
    }


}
