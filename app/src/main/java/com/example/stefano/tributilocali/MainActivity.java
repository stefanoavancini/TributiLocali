package com.example.stefano.tributilocali;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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


public class MainActivity extends Activity {
    public String USER  ="";
    public String PASSWORD  ="";
    public Integer MATRICOLA = 0;
    private String XML = "";
    public String ragione_sociale,auth,error;
    private TextView benvenuto;
    private EditText username,password;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final XML_data globalVariable = (XML_data) getApplicationContext();
        //final XML_data globalVariable = (XML_data) getApplicationContext();
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        benvenuto = (TextView) findViewById(R.id.benvenuto);
        //Button bplay = (Button) findViewById(R.id.category_match);
        Button accedi = (Button) findViewById(R.id.accedi);
        if(globalVariable.getUsername() == null) {
            accedi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    new MyAsyncTask().execute(username.getText().toString(), password.getText().toString(), "SI", "");
                }
            });
        }
        else
        {

        }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
            //Scanner scan = new Scanner(result.toString()); // I have named your StringBuilder object sb
            XMLParser parser = new XMLParser();
            if ((result.toString().equals("Connection problem 1")) | (result.toString().equals("Connection problem 2")) | (result.toString().equals("Parameters error"))) {
                //benvenuto.setText("VERIFICA LA CONNESSIONE");
                showSimplePopUp("VERIFICA LA CONNESSIONE");
            }
            else {
                Document doc = parser.getDomElement(result.toString()); // getting DOM element
                NodeList nl = doc.getElementsByTagName("utente");

                for (int i = 0; i < nl.getLength(); i++) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    Element e = (Element) nl.item(i);
                    ragione_sociale = parser.getValue(e, "ragione_sociale");
                    auth = parser.getValue(e, "autorizzazione");
                    MATRICOLA = Integer.parseInt(parser.getValue(e, "matricola"));
                    error = parser.getValue(e, "errore");
                }
                if (error.equals("EP")) {
                    showSimplePopUp("PASSWORD ERRATA");
                    //benvenuto.setText("PASSWORD ERRATA 1");
                    //Intent intent = new Intent(getApplicationContext(), settings.class);
                    //startActivity(intent);
                } else if (error.equals("EPN")) {
                    //String[] separated = line.split(";");
                    showSimplePopUp("PASSWORD ERRATA");
                    //benvenuto.setText("PASSWORD ERRATA 2");
                    //USER = separated[0];
                    //PASSWORD = separated[1];
                } else if (error.equals("EC")) {
                    //String[] separated = line.split(";");
                    showSimplePopUp("ERRORE CONNESSIONE");
                    //benvenuto.setText("ERRORE CONNESSIONE");
                    //USER = separated[0];
                    //PASSWORD = separated[1];
                } else if (error.equals("EU")) {
                    //String[] separated = line.split(";");
                    showSimplePopUp("UTENTE NON ABILITATO");
                    //benvenuto.setText("UTENTE NON ABILITATO 1");
                    //USER = separated[0];
                    //PASSWORD = separated[1];
                } else if (error.equals("EUN")) {
                    //String[] separated = line.split(";");
                    showSimplePopUp("UTENTE NON ABILITATO");
                    //benvenuto.setText("UTENTE NON ABILITATO 2");
                    //USER = separated[0];
                    //PASSWORD = separated[1];
                } else {
                    if (error.equals("OK")) {
                        if (!auth.equals("")) {
                            benvenuto.setText("ADMIN " + ragione_sociale.trim() + "," + "," + auth);
                        } else {
                            benvenuto.setText("BENVENUTO " + ragione_sociale.trim() + "," + "," + auth);
                        }
                        XML = result.toString();
                        Intent myIntent = new Intent(MainActivity.this, Contribuente.class);
                        //myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        myIntent.putExtra("XML", XML); //Optional parameters
                        myIntent.putExtra("MTR", MATRICOLA); //Optional parameters

                        final XML_data globalVariable = (XML_data) getApplicationContext();
                        globalVariable.setUsername(username.getText().toString());
                        globalVariable.setPassword(password.getText().toString());
                        //globalVariable.setAuth(auth);
                        startActivity(myIntent);
                    } else {
                        benvenuto.setText("ERRORE DURANTE LA VERIFICA DEI DATI. SI PREGA DI RIPROVARE PIù TARDI");
                    }//USER = separated[0];
                    //PASSWORD = separated[1];
                }
            }
                //System.out.println(oneLine.length());

            //error.setText(line);
        }
        protected void onProgressUpdate(Integer... progress){            //error.setText(progress[0]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(MainActivity.this, "Attendere", "Caricamento...");
        }

        public StringBuilder postData(String paramUsername, String paramid_dispositivo, String paramimage, String paramlanguage) {
            // Create a new HttpClient and Post Header
            StringBuilder stringBuilder = new StringBuilder();
            //System.out.println("*** doInBackground ** paramUsername " + paramUsername + " paramid_dispositivo :" + paramid_dispositivo + " paramimage :" + paramimage + " paramlanguage :" + paramlanguage);

            HttpClient httpClient = new DefaultHttpClient();

            // In a POST request, we don't pass the values in the URL.
            //Therefore we use only the web page URL as the parameter of the HttpPost argument
            HttpPost httpPost = new HttpPost("http://www.gestelsrl.it/php/login.php");
            progressDialog.setMessage("Verifica utente in corso...");
            // Because we are not passing values over the URL, we should have a mechanism to pass the values that can be
            //uniquely separate by the other end.
            //To achieve that we use BasicNameValuePair
            //Things we need to pass with the POST request
            BasicNameValuePair usernameBasicNameValuePair = new BasicNameValuePair("username", paramUsername);
            BasicNameValuePair dispositivoBasicNameValuePAir = new BasicNameValuePair("password", paramid_dispositivo);
            BasicNameValuePair imageBasicNameValuePAir = new BasicNameValuePair("delegati", paramimage);
            BasicNameValuePair languageBasicNameValuePAir = new BasicNameValuePair("language", paramlanguage);
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
