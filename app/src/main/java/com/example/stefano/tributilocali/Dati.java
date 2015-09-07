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

    private String descrizionecategoria(String categoria)
    {
    if(categoria=="A01")
       return "ABITAZIONI DI TIPO SIGNORILE";
    else if(categoria=="A02")
        return "ABITAZIONI DI TIPO CIVILE";
    else if(categoria=="A03")
        return"ABITAZIONI DI TIPO ECONOMICO";
    else if(categoria=="A04")
        return "ABITAZIONI DI TIPO POPOLARE";
    else if(categoria=="A05")
        return "ABITAZIONI DI TIPO ULTRAPOPOLARE";
    else if(categoria=="A06")
        return "ABITAZIONI DI TIPO RURALE";
    else if(categoria=="A07")
        return "ABITAZIONI IN VILLINI";
    else if(categoria=="A08")
        return "ABITAZIONI IN VILLE";
    else if(categoria=="A09")
        return "CASTELLI,PALAZZI DI EMINENTI PREGI ARTISTICI E STORICI";
    else if(categoria=="A10")
        return "UFFICI E STUDI PRIVATI";
    else if(categoria=="A11")
        return "ABITAZIONI ED ALLOGGI TIPICI DEI LUOGHI";
    else if(categoria=="B01")
        return "COLLEGI E CONVITTI, EDUCANDATI, RICOVERI, ORFANOTROFI, OSPIZI, CONVENTI, SEMINARI, CASERME";
    else if(categoria=="B02")
        return "CASE DI CURA ED OSPEDALI";
    else if(categoria=="B03")
        return "PRIGIONI E RIFORMATORI";
    else if(categoria=="B04")
        return "UFFICI PUBBLICI";
    else if(categoria=="B05")
        return "SCUOLE, LABORATORI SCIENTIFICI";
    else if(categoria=="B06")
        return "BIBLIOTECHE, PINACOTECHE, MUSEI, GALLERIE, ACCADEMIE CHE NON HANNO SEDE IN EDIFICI DELLA CATEGORIA A/09";
    else if(categoria=="B07")
        return "CAPPELLE ED ORATORI NON DESTINATI ALL`ESERCIZIO PUBBLICO DEI CULTI";
    else if(categoria=="B08")
        return "MAGAZZINI SOTTERANEI PER DEPOSITO DERRATE";
    else if(categoria=="C01")
        return "NEGOZI E BOTTEGHE";
    else if(categoria=="C02")
        return "MAGAZZINI E LOCALI DI DEPOSITO";
    else if(categoria=="C03")
        return "LABORATORI PER ARTI E MESTIERI";
    else if(categoria=="C04")
        return "FABBRICATI E LOCALI PER ESERCIZI SPORTIVI (SENZA FINI DI LUCRO)";
    else if(categoria=="C05")
        return "STABILIMENTI BALNEARI E DI ACQUE CURATIVE (SENZA FINI DI LUCRO)";
    else if(categoria=="C06")
        return "STALLE, SCUDERIE, RIMESSE E AUTORIMESSE (SENZA FINI DI LUCRO)";
    else if(categoria=="C07")
        return "TETTOIE CHIUSE OD APERTE";
    else if(categoria=="D01")
        return "OPIFICI";
    else if(categoria=="D02")
        return "ALBERGHI E PENSIONI";
    else if(categoria=="D03")
        return "TEATRI, CINEMATOGRAFI, SALE PER CONCERTI E SPETTACOLI E SIMILI (CON FINI DI LUCRO)";
    else if(categoria=="D04")
        return "CASE DI CURA ED OSPEDALI";
    else if(categoria=="D05")
        return "ISTITUTO DI CREDITO, CAMBIO E ASSICURAZIONE (CON FINI DI LUCRO)";
    else if(categoria=="D06")
        return "FABBRICATI E LOCALI PER ESERCIZI SPORTIVI (CON FINI DI LUCRO)";
    else if(categoria=="D07")
        return "FABBRICATI COSTRUITI O ADATTATI PER LE SPECIALI ESIGENZE DI UN'ATTIVIT&Agrave; INDUSTRIALE";
    else if(categoria=="D08")
        return "FABBRICATI COSTRUITI O ADATTATI PER LE SPECIALI ESIGENZE DI UN'ATTIVIT&Agrave; COMMERCIALE ";
    else if(categoria=="D09")
        return "EDIFICI GALLEGGIANTI O SOSPESI ASSICURATI A PUNTI FISSI DEL SUOLO, PONTI PRIVATI SOGGETTI A PEDAGGIO";
    else if(categoria=="D10")
        return "FABBRICATI CON FUNZIONI PRODUTTIVE CONNESSE ALLE ATTIVIT&Agrave; AGRICOLE";
    else if(categoria=="FBR")
        return "AREA FABBRICABILE";
    else if(categoria=="T00")
        return "MUSEI, BIBLIOTECHE, SCUOLE, ASSOCIAZIONI, LUOGHI DI CULTO";
    else if(categoria=="TT0")
        return "CINEMATOGRAFI E TEATRI";
    else if(categoria=="KK0")
        return "CAUTORIMESSE E MAGAZZINI SENZA ALCUNA VENDITA DIRETTA";
    else if(categoria=="C00")
        return "CAMPEGGI, DISTRIBUTORI CARBURANTI, IMPIANTI SPORTIVI";
    else if(categoria=="CC0")
        return "STABILIMENTI BALNEARI";
    else if(categoria=="E00")
        return "ESPOSIZIONI, AUTOSALONI";
    else if(categoria=="A00")
        return "ALBERGHI CON RISTORANTE";
    else if(categoria=="AA0")
        return "ALBERGHI SENZA RISTORANTE";
    else if(categoria=="X00")
        return "CASE DI CURA E RIPOSO";
    else if(categoria=="XX0")
        return "OSPEDALI";
    else if(categoria=="Q00")
        return "UFFICI, AGENZIE, STUDI PROFESSIONALI";
    else if(categoria=="U00")
        return "BANCHE E ISTITUTI DI CREDITO";
    else if(categoria=="N00")
        return "NEGOZI ABBIGLIAMENTO, CALZATURE, LIBRERIA, CARTOLERIA, FERRAMENTA E ALTRI BENI DUREVOLI";
    else if(categoria=="F00")
        return "EDICOLE, FARMACIE, TABACCAIO, PLURILICENZE";
    else if(categoria=="P00")
        return "NEGOZI PARTICOLARI QUALI FILATELIA, TENDE E TESSUTI, TAPPETI, CAPPELLI E OMBRELLI, ANTIQUARIATO";
    else if(categoria=="Z00")
        return "BANCHI DI MERCATO BENI DUREVOLI";
    else if(categoria=="V00")
        return "ATTIVITÀ ARTIGIANALI TIPO BOTTEGHE: PARRUCCHIERE, BARBIERE, ESTETISTA";
    else if(categoria=="W00")
        return "ATTIVITÀ ARTIGIANALI TIPO BOTTEGHE: FALEGNAME, IDRAULICO, FABBRO, ELETTRICISTA";
    else if(categoria=="K00")
        return "CARROZZERIA, AUTOFFICINA, ELETTRAUTO";
    else if(categoria=="Y00")
        return "ATTIVITÀ INDUSTRIALI CON CAPANNONI DI PRODUZIONE";
    else if(categoria=="WW0")
        return "ATTIVITÀ ARTIGIANALI DI PRODUZIONE BENI SPECIFICI";
    else if(categoria=="R00")
        return "RISTORANTI, TRATTORIE, OSTERIE, PIZZERIE, PUB";
    else if(categoria=="M00")
        return "MENSE, BIRRERIE, AMBURGHERIE";
    else if(categoria=="B00")
        return "BAR, CAFFE, PASTICCERIA";
    else if(categoria=="S00")
        return "SUPERMERCATO, PANE E PASTA, MACELLERIA, SALUMI E FORMAGGI, GENERI ALIMENTARI";
    else if(categoria=="J00")
        return "PLURILICENZE ALIMENTARI E/O MISTE";
    else if(categoria=="O00")
        return "ORTOFRUTTA, PESCHERIE, FIORI E PIANTE, PIZZA AL TAGLIO, GELATERIE D'ASPORTO";
    else if(categoria=="I00")
        return "IPERMERCATI DI GENERI MISTI";
    else if(categoria=="ZZ0")
        return "BANCHI DI MERCATO GENERE ALIMENTARI";
    else if(categoria=="DD0")
        return "DISCOTECHE, NIGHT CLUB";
    else if(categoria=="DOM")
        return "UTENZA DOMESTICA ABITAZIONE";
    else if(categoria=="DOG")
        return "GARAGE/POSTO AUTO";
    else if(categoria=="DOP")
        return "ALTRE PERTINENZE";
    else if(categoria=="???")
        return "DIFFERENZE DOVUTE";
    else if(categoria=="!!!")
        return "NOTA DI ADDEBITO";
    else if(categoria=="***")
        return "NOTA DI RIMBORSO";
    else if(categoria=="100")
        return "ESCLUSO IN QUANTO IL RIFIUTO VIENE RIUTILIZZATO";
    else if(categoria=="101")
        return "ESCLUSO PER SUPERAMENTO LIMITI";
    else if(categoria=="102")
        return "ESCLUSO IN QUANTO RIFIUTO SPECIALE";
    else if(categoria=="103")
        return "ESCLUSO IN QUANTO LA PRESENZA UMANA È SPORADICA";
    else if(categoria=="104")
        return "CARICAMENTO DI COMODO PER CRM";
    else if(categoria=="21")
        return "ISCRITTI AIRE";
    else if(categoria=="22")
        return "DANNO ECONOMICO PER PRECLUS TRAFFICO";
    else if(categoria=="23")
        return "ABITAZ. CON PRATICHE DI COMPOSTAGGIO";
    else if(categoria=="24")
        return "PERS.ULTRA 65 CON REDD.MINORE DI";
    else if(categoria=="25")
        return "NUCLEI 6 O + COMP.CON REDD.MINORE DI";
    else if(categoria=="26")
        return "SCUOLE PUBBLICHE 1 GRADO (ELEM.-MEDIE)";
    else if(categoria=="27")
        return "GRAVI SITUAZIONI A DISCREZIONE GIUNTA C.";
    else if(categoria=="29")
        return "CASA GEN.PICCOLE SUORE SACRA FAM.";
    else if(categoria=="30")
        return "UBICAZ.ESTERNA ALLA ZONA SERVIZIO";
    else if(categoria=="31")
        return "ENTI O ASSOC. SENZA FINI DI LUCRO";
    else if(categoria=="32")
        return "STAGIONALITA'";
    else if(categoria=="34")
        return "LOC.COMUNE/UNIONE LOCATI ASS.SENZA LUCRO";
    else if(categoria=="40")
        return "ESENZIONI PER MINIMO VITALE";
    else if(categoria=="50")
        return "FAMIGLIE CON SOGGETTI DIVERS.ABILI";
    else if(categoria=="51")
        return "FORZE E ARMATE SIA MILITARI CHE CIVILI";
    else if(categoria=="52")
        return "ABIT.DISPOS.RESIDENTI";
    else if(categoria=="53")
        return "RESIDENTI > 4 COMPONENTI";
    else if(categoria=="54")
        return "USO DISCONTINUO";
    else if(categoria=="55")
        return "UTIL.ESCLUS.ASS.SPORTIVE";
    else if(categoria=="56")
        return "ABIT.A DISPOSIZ.RES.+ZONE NON SERV.";
    else if(categoria=="70")
        return "CASE RIPOSO QUOTA RIF.SPECIALI";
    else if(categoria=="71")
        return "AMBULATORI VETERINARI";
    else if(categoria=="80")
        return "LABORATORI FOTOGRAFICI ED ELIOGRAFICI";
    else if(categoria=="81")
        return "LAVANDERIE A SECCO";
    else if(categoria=="82")
        return "ELETTRAUTO E GOMMISTI";
    else if(categoria=="83")
        return "TIPOG. STAMPERIE SERIGR. INCIS. VETRERIE";
    else if(categoria=="84")
        return "MANUFAT. DI VERNICIATURA GALVANOT.CERAMI";
    else if(categoria=="85")
        return "AUTOCARROZZERIE AUTOFFICINE";
    else if(categoria=="86")
        return "LATTON.CARP.METALLICA LAV.PLAST.MECC.";
    else if(categoria=="87")
        return "LABORATOTIO ODONTOTECNICO";
    else if(categoria=="88")
        return "SEGHERIE E FALEGNAMERIE";
    else if(categoria=="89")
        return "AMBULATORI DENTISTICI";
    else if(categoria=="98")
        return "SOSPESO";
    return categoria;
    }

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

                                                //String categoria = parser.getValue(eeee,KEY_CATEGORIA) + " Rendita " + parser.getValue(eeee,KEY_RENDITA);
                                                String descrizione_categoria = descrizionecategoria(parser.getValue(eeee,KEY_CATEGORIA));
                                                String rendita = parser.getValue(eeee, KEY_RENDITA);
                                                Double rendbl = Double.parseDouble(rendita);
                                                String quota = parser.getValue(eeee,KEY_QUOTA) + "% Mesi: " + parser.getValue(eeee,KEY_MESI);
                                                rendita = "Rendita € " + String.format(Locale.GERMANY,"%.2f", rendbl) + " Possesso: " + quota + "";
                                                map.put(KEY_CATEGORIA, rendita);

                                            String dati_catastali = "C.C. " + parser.getValue(eeee, KEY_SEZIONE) + "P.Ed. " + parser.getValue(eeee,KEY_PED);
                                            if(!parser.getValue(eeee,KEY_PED_BARRATO).equals("0")){
                                                dati_catastali += "/" + parser.getValue(eeee,KEY_PED_BARRATO);
                                            }
                                            dati_catastali += " Sub. " + parser.getValue(eeee,KEY_SUBALTERNO) + " - " + descrizione_categoria;
                                            String via = parser.getValue(eeee,KEY_VIA).trim() + " " + parser.getValue(eeee,KEY_CIVICO) + " " + parser.getValue(eeee,KEY_BARRATO);

                                            String importo = parser.getValue(eeee, KEY_TOTALE);
                                            Double impdbl = Double.parseDouble(importo);

                                                map.put(KEY_VIA, via);
                                                map.put(KEY_SEZIONE, dati_catastali);
                                                //map.put(KEY_QUOTA, quota);
                                                map.put(KEY_ABIT_PRINC, "Abitazione principale: " + parser.getValue(eeee, KEY_ABIT_PRINC));
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
