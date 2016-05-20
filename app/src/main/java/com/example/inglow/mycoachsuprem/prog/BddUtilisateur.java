package com.example.inglow.mycoachsuprem.prog;

import java.util.HashMap;

/**
 * Created by inglow on 09/02/16.
 */
public class BddUtilisateur {
    private static HashMap<String, Utilisateur> lesUsers = new HashMap<String, Utilisateur>();

    public static void remplirDonnees()
    {
        Utilisateur unUser= new Utilisateur("Jouanny", "Jérome", "admin@gmail.com","123");
        lesUsers.put(unUser.getEmail(), unUser);
    }
    public static Utilisateur verifidentifiants(String email, String mdp)
    {
        Utilisateur unUser= null;
        if(lesUsers.containsKey(email))
        {
            unUser=lesUsers.get(email);
            if(unUser.getMdp().equals(mdp))
            {
                return unUser;
            }
            else
            {
                return null;
            }
        }
        return null;
    }
}
