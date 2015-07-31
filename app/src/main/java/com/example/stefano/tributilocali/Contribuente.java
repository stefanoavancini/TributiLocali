package com.example.stefano.tributilocali;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Avancini.Stefano on 28/07/2015.
 */
public class Contribuente extends Activity {
    ListView listitem;
    Element e;
    static final String KEY_DELEGATI = "delegato"; // parent node
    static final String KEY_UTENTE = "utente"; // parent node
    static final String KEY_TRIBUTI = "delegati"; // parent node
    static final String KEY_TIPO = "tipo";
    static final String KEY_RGS = "ragione_sociale";
    static final String KEY_CF = "codice_fiscale";
    static final String KEY_MTR = "matricola";
    private String ragione_sociale,codice_fiscale,matricola,username,password;
    private ProgressDialog progressDialog;

    ArrayList<HashMap<String, String>> albumsList;
    public String XML;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final XML_data globalVariable = (XML_data) getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_contribuente);
        Intent intent = getIntent();
        int delegati_n = 0;
        XML = intent.getStringExtra("XML");
        username = intent.getStringExtra("username");
        password = intent.getStringExtra("password");
        listitem = (ListView) findViewById(R.id.lv_contribuente);

        XMLParser parser = new XMLParser();
        Document doc = parser.getDomElement(XML); // getting DOM element
        NodeList nl = doc.getElementsByTagName(KEY_DELEGATI);
        albumsList = new ArrayList<HashMap<String, String>>();
        if(nl.getLength()>0) {
            for (int i = 0; i < nl.getLength(); i++) {
                HashMap<String, String> map = new HashMap<String, String>();
                e = (Element) nl.item(i);
                String cdf = parser.getValue(e, KEY_CF).trim();
                if (cdf.length() >= 6) {
                    map.put(KEY_TRIBUTI, parser.getValue(e, KEY_RGS));
                    String tipo = parser.getAttribute(e, KEY_TIPO);
                    String altri_dati = "";
                    if (tipo.equals("D")) {
                        altri_dati = "DELEGATO: " + parser.getValue(e, KEY_CF).trim() + " - " + parser.getValue(e, KEY_MTR);
                        delegati_n++;
                    } else {
                        ragione_sociale = parser.getValue(e, KEY_RGS);
                        matricola = parser.getValue(e, KEY_MTR);
                        codice_fiscale = parser.getValue(e, KEY_RGS);
                        altri_dati = parser.getValue(e, KEY_CF).trim() + " - " + parser.getValue(e, KEY_MTR);
                    }
                    map.put("ALTRI_DATI", altri_dati);
                    map.put("MATRICOLA",parser.getValue(e, KEY_MTR));
                    if (!albumsList.contains(map)) {
                        albumsList.add(map);
                    }
                }

            }
        }
        if(delegati_n==0)
        {
            //vai al comune
        }
        ListAdapter adapter = new SimpleAdapter(
                Contribuente.this, albumsList,R.layout.data_contribuente_riga, new String[] { KEY_TRIBUTI,"ALTRI_DATI","MATRICOLA"}, new int[] {R.id.Contribuente,R.id.Codice_fiscale,R.id.Matricola});
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
                String matricola = ((TextView) view.findViewById(R.id.Matricola)).getText().toString();
                if (globalVariable.getXMLdata(matricola) == null) {
                    new MyAsyncTask().execute(username, password, "", matricola);
                }
                else
                {
                    Intent i = new Intent(Contribuente.this, Comune.class);
                    // send album id to tracklist activity to get list of songs under that album
                    String contribuente = ((TextView) findViewById(R.id.Contribuente)).getText().toString();
                    i.putExtra("contribuente", contribuente.trim());
                    i.putExtra("XML",globalVariable.getXMLdata(matricola));
                    startActivity(i);
                }


            }
        });
    }

    private void showSimplePopUp(String errore) {

        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
        helpBuilder.setTitle("Errore");
        helpBuilder.setMessage(errore);
        helpBuilder.setPositiveButton("Esci",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                    }
                });

        // Remember, create doesn't show the dialog
        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();
    }

    private class MyAsyncTask extends AsyncTask<String, Integer, StringBuilder> {
        @Override
        protected StringBuilder doInBackground(String... params) {
            // TODO Auto-generated method stub
            return postData(params[0],params[1],params[2],params[3]);
        }

        public void processFinish(Object output) {
            //error.setText((String) output);
        }
        protected void onPostExecute(StringBuilder result){
            //result.
            progressDialog.dismiss();
            String line = "";
            Scanner scan = new Scanner(result.toString()); // I have named your StringBuilder object sb
            XMLParser parser = new XMLParser();
            if ((result.toString().equals("Connection problem 1")) | (result.toString().equals("Connection problem 2")) | (result.toString().equals("Parameters error"))) {
                //benvenuto.setText("VERIFICA LA CONNESSIONE");
                showSimplePopUp("VERIFICA LA CONNESSIONE");
            }
            else {
                Document doc = parser.getDomElement(result.toString()); // getting DOM element
                XML = result.toString();
                final XML_data globalVariable = (XML_data) getApplicationContext();
                globalVariable.setXMLdata(matricola,XML);
                Intent i = new Intent(Contribuente.this, Comune.class);
                // send album id to tracklist activity to get list of songs under that album
                String contribuente = ((TextView) findViewById(R.id.Contribuente)).getText().toString();

                i.putExtra("contribuente", contribuente.trim());
                i.putExtra("XML",XML);
                startActivity(i);
                }

            //System.out.println(oneLine.length());

            //error.setText(line);
        }
        protected void onProgressUpdate(Integer... progress){            //error.setText(progress[0]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(Contribuente.this, "Attendere", "Caricamento...");
        }

        public StringBuilder postData(String paramUsername, String paramid_dispositivo, String paramimage, String paramlanguage) {
            // Create a new HttpClient and Post Header
            StringBuilder stringBuilder = new StringBuilder();
            //System.out.println("*** doInBackground ** paramUsername " + paramUsername + " paramid_dispositivo :" + paramid_dispositivo + " paramimage :" + paramimage + " paramlanguage :" + paramlanguage);

            HttpClient httpClient = new DefaultHttpClient();

            // In a POST request, we don't pass the values in the URL.
            //Therefore we use only the web page URL as the parameter of the HttpPost argument
            HttpPost httpPost = new HttpPost("http://www.gestelsrl.it/php/login.php");
            //progressDialog.setMessage("Verifica utente in corso...");
            // Because we are not passing values over the URL, we should have a mechanism to pass the values that can be
            //uniquely separate by the other end.
            //To achieve that we use BasicNameValuePair
            //Things we need to pass with the POST request
            BasicNameValuePair usernameBasicNameValuePair = new BasicNameValuePair("username", paramUsername);
            BasicNameValuePair dispositivoBasicNameValuePAir = new BasicNameValuePair("password", paramid_dispositivo);
            BasicNameValuePair imageBasicNameValuePAir = new BasicNameValuePair("delegati", paramimage);
            BasicNameValuePair languageBasicNameValuePAir = new BasicNameValuePair("matricola", paramlanguage);
            // We add the content that we want to pass with the POST request to as name-value pairs
            //Now we put those sending details to an ArrayList with type safe of NameValuePair
            List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
            nameValuePairList.add(usernameBasicNameValuePair);
            nameValuePairList.add(dispositivoBasicNameValuePAir);
            nameValuePairList.add(imageBasicNameValuePAir);
            nameValuePairList.add(languageBasicNameValuePAir);

            try {
                // UrlEncodedFormEntity is an entity composed of a list of url-encoded pairs.
                //This is typically useful while sending an HTTP POST request.
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairList);

                // setEntity() hands the entity (here it is urlEncodedFormEntity) to the request.
                httpPost.setEntity(urlEncodedFormEntity);

                try {
                    // HttpResponse is an interface just like HttpPost.
                    //Therefore we can't initialize them
                    HttpResponse httpResponse = httpClient.execute(httpPost);

                    // According to the JAVA API, InputStream constructor do nothing.
                    //So we can't initialize InputStream although it is not an interface
                    InputStream inputStream = httpResponse.getEntity().getContent();

                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    String bufferedStrChunk = null;

                    while ((bufferedStrChunk = bufferedReader.readLine()) != null) {
                        stringBuilder.append(bufferedStrChunk);
                    }


                } catch (ClientProtocolException cpe) {
                    stringBuilder.append("Connection problem 1");
                } catch (IOException ioe) {
                    stringBuilder.append("Connection problem 2");
                }

            } catch (UnsupportedEncodingException uee) {
                stringBuilder.append("Parameters error");
            }
            return stringBuilder;
        }

    }
}
