package com.example.inglow.mycoachsuprem;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.inglow.mycoachsuprem.prog.Coach;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class LesCoachs extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static ArrayList<Coach> uneListe = new ArrayList<Coach>();
    private ListView affListe;
    private Button call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_les_coachs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        this.affListe = (ListView) findViewById(R.id.idListe);




        // création d'un processus pour l'exécution de la tache asynchrone
        Thread unT = new Thread(new Runnable() {
            @Override
            public void run() {
                //instanciation de la classe ExecLister
                ExecLister unExec = new ExecLister();
                unExec.execute();
                //ici : affichage une fois le processus de la tache terminé

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final ArrayList<HashMap<String, String>> donnees =
                                new ArrayList<HashMap<String, String>>();
                        // traitement de la liste des produits
                        for (Coach unProd : uneListe) {
                            HashMap<String, String> uneMap =
                                    new HashMap<String, String>();
                            uneMap.put("nom", unProd.getNom());
                            uneMap.put("prenom", unProd.getPrenom());
                            uneMap.put("email", unProd.getEmail());
                            uneMap.put("avatar", unProd.getAvatar());
                            uneMap.put("cp", unProd.getCp() + "");
                            uneMap.put("téléphone", unProd.getTelephone() + "");
                            donnees.add(uneMap);
                        }


                        // construction des intitulés
                        String[] from = {"nom", "prenom", "email", "avatar", "cp", "telephone"};
                        int[] to = {R.id.idnom, R.id.idprenom, R.id.idemail2, R.id.idavatar, R.id.idcp, R.id.idtelephone};
                        // définition de l'adaptateur de l'affichage
                        SimpleAdapter unAdapter = new SimpleAdapter(getApplicationContext(), donnees
                                , R.layout.affiche_liste_coach, from, to);
                        affListe.setAdapter(unAdapter);

                    }
                });
            }
        });
        // démarrage du processus
        unT.start();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.les_coachs, menu);
        return true;
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static void setListe(ArrayList<Coach> uneL) {
        // accésseur sur la liste
        LesCoachs.uneListe = uneL;
    }

    private void call() {
        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("0000000000"));
        try {
            startActivity(in);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(), "yourActivity is not founded", Toast.LENGTH_SHORT).show();
        }
    }


    public void onClick(View v) {
        switch (v.getId()) {


        }
    }
}
class ExecLister extends AsyncTask<Void, Void, ArrayList<Coach> >
{

    @Override
    public  void onPostExecute (ArrayList<Coach> uneListe){
        LesCoachs.setListe(uneListe);
    }
    @Override
    protected ArrayList<Coach> doInBackground(Void... unProd) {
        ArrayList<Coach> uneListe = new ArrayList<Coach>();
        String url = "http://jayjay-dev.fr/AppliMobile/listerCoach.php";
        HttpURLConnection urlConnection = null;
        URL uneUrl = null;
        String chaine ="";
        try
        {
            // Instanciation de l'url
            uneUrl = new URL(url);
            // Ouverture de la connexion
            urlConnection = (HttpURLConnection)uneUrl.openConnection();
            // Choix de la méthode de transfert
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            // Ouverture des ports pour l'envoi et la réception
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            // Envoi des données via un buffer

            urlConnection.connect();
            OutputStream fichierOut = new BufferedOutputStream(urlConnection.getOutputStream());
            BufferedWriter buffer = new BufferedWriter(new OutputStreamWriter(fichierOut, "utf-8"));

            // On vide le buffer puis on le ferme
            buffer.flush();
            buffer.close();
            fichierOut.close();
            InputStream in = urlConnection.getInputStream();

            /* RequiresPermission.Read bytes to the Buffer until there is nothing more to read(-1) */
            BufferedReader reader = null;
            StringBuffer response = new StringBuffer();
            try {
                reader = new BufferedReader(new InputStreamReader(in));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                    Log.e("aByteArrayBuffer ", line);
                }
                chaine = response.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Log.e("Connexion : ", "Connexion réussie.");
        }
        catch (IOException exp)
        {

            Log.e("Erreur :", "Erreur de connexion à : " + url);
        }
        try
        {
            JSONArray tabJson = new JSONArray(chaine);
            for (int i=0; i<tabJson.length(); i++)
            {
                JSONObject unObjet = tabJson.getJSONObject(i);
                String nom = unObjet.getString("nom");
                String prenom = unObjet.getString("prenom");
                String email = unObjet.getString("email");
                String avatar = unObjet.getString("avatar");
                int cp = unObjet.getInt("cp");
                int telephone = unObjet.getInt("telephone");

                Coach unP = new Coach(nom,prenom, email, avatar, telephone, cp);
                uneListe .add(unP);
                Log.e("nom", nom);
            }
        }
        catch (JSONException exp)
        {
            Log.e("Erreur", "Erreur de parse du Json.");
        }
        return uneListe;
    }
}
