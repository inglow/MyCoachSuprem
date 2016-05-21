package com.example.inglow.mycoachsuprem;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inglow.mycoachsuprem.prog.BddUtilisateur;
import com.example.inglow.mycoachsuprem.prog.Utilisateur;

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

public class Connexion extends AppCompatActivity implements View.OnClickListener {
    private TextView tvTitre;
    private EditText txtmail, txtMdp;
    private static Utilisateur unUser;
    private Button btAnnuler, btSeConnecter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);
        this.txtmail=(EditText)findViewById(R.id.idemail);
        this.txtMdp=(EditText)findViewById(R.id.idmdp);
        this.btAnnuler= (Button) findViewById(R.id.idannuler);
        this.btSeConnecter=(Button)findViewById(R.id.idconnexion);
        this.btAnnuler.setOnClickListener(this);
        this.btSeConnecter.setOnClickListener(this);
        BddUtilisateur.remplirDonnees();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

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
    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.idannuler:
                this.txtmail.setText("jouanny.jerome@gmail.com");
                this.txtMdp.setText("92i");
                break;

            case R.id.idconnexion:
                final String email=this.txtmail.getText().toString();
                final String mdp=this.txtMdp.getText().toString();
                final Connexion st =this;
                Thread unT=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ExecVerif unExec= new ExecVerif();
                        unExec.execute(email,mdp);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(unUser==null)
                                {

                                    Toast.makeText(st,"Erreur identifiants", Toast.LENGTH_LONG).show();
                                    txtmail.setText("");
                                    txtMdp.setText("");

                                }
                                else
                                {
                                    Toast.makeText(st, "Bienvenue"+unUser.getNom()+""+unUser.getPrenom(),Toast.LENGTH_LONG).show();
                                    //appel de la vue du menu
                                    Intent unIntent = new Intent(st, LesRdv.class);
                                    unIntent.putExtra("nom", unUser.getNom());
                                    unIntent.putExtra("prenom", unUser.getPrenom());
                                    startActivity(unIntent);
                                }
                            }
                        });

                    }
                });

                unT.start();
                break;
        }

    }
    public static void setUser(Utilisateur unU)
    {
        Connexion.unUser=unU;
    }
}
class ExecVerif extends AsyncTask<String, Void, Utilisateur>
{


    @Override
    protected Utilisateur doInBackground(String... deschaines) {
        Utilisateur unUser=null;
        String email =deschaines[0];
        String mdp =deschaines[1];
        String parametres="email="+email+"&mdp="+mdp;
        String url = "http://jayjay-dev.fr/stock/veriflogin.php";
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
            buffer.write(parametres);
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

            JSONObject unObjet = tabJson.getJSONObject(0);
            String nom = unObjet.getString("nom");
            String prenom = unObjet.getString("prenom");
            int nb = unObjet.getInt("nb");
            if(nb==1)
            {
                unUser= new Utilisateur(nom, prenom, email, mdp);
            }


        }
        catch (JSONException exp)
        {
            Log.e("Erreur", "Erreur de parse du Json.");
        }
        return unUser;
    }
    protected void onPostExecute(Utilisateur unUser)
    {
        Connexion.setUser(unUser);
    }
}
