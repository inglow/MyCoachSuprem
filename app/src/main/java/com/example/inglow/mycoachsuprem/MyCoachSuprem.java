package com.example.inglow.mycoachsuprem;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.inglow.mycoachsuprem.prog.Coach;

import java.util.ArrayList;
import java.util.HashMap;
///S

public class MyCoachSuprem extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static ArrayList<Coach> uneListe=new ArrayList<Coach>();
    private ListView affListe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_coach_suprem);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.affListe=(ListView)findViewById(R.id.idListe);

        // création d'un processus pour l'exécution de la tache asynchrone
        Thread unT=new Thread(new Runnable() {
            @Override
            public void run() {
                //instanciation de la classe ExecLister
                ExecLister unExec=new ExecLister();
                unExec.execute();
                //ici : affichage une fois le processus de la tache terminé

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final ArrayList<HashMap<String, String>> donnees=
                                new ArrayList<HashMap<String, String>>();
                        // traitement de la liste des produits
                        for (Coach unProd : uneListe){
                            HashMap<String, String> uneMap=
                                    new HashMap<String, String>();
                            uneMap.put("reference",unProd.getReference());
                            uneMap.put("designation",unProd.getDesignation());
                            uneMap.put("prix",unProd.getPrix()+"");
                            uneMap.put("qte",unProd.getQte()+"");
                            uneMap.put("categorie",unProd.getCategorie());
                            donnees.add(uneMap);
                        }


                        // construction des intitulés
                        String [] from={"reference","designation", "prix", "qte", "categorie"};
                        int [] to = {R.id.idreference, R.id.iddesignation, R.id.idprix,R.id.idqte,R.id.idcategorie};
                        // définition de l'adaptateur de l'affichage
                        SimpleAdapter unAdapter=new SimpleAdapter(getApplicationContext(), donnees
                                ,R.layout.affiche_liste, from,to);
                        affListe.setAdapter(unAdapter);
                    }
                });
            }
        });
        // démarrage du processus
        unT.start();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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
        getMenuInflater().inflate(R.menu.my_coach_suprem, menu);
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
            Intent intent = new Intent(this, MyCoachSuprem.class);
            startActivity(intent);

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            Intent intent = new Intent(this, MesRdv.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
