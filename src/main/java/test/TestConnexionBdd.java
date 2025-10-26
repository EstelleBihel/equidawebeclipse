package test;

import database.Connexionbdd;
import dao.DaoCheval;
import dao.DaoVente;
import model.Cheval;
import model.Vente;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class TestConnexionBdd {

    public static void main(String[] args) {

        Connection cnx = null;

        try {
            // 1. Ouvrir la connexion
            cnx = Connexionbdd.ouvrirConnexion();
            System.out.println(">>> Connexion OK : " + cnx);

            // 2. Tester récupération d'un cheval précis
            int idCheval = 3;
            Cheval ch = DaoCheval.getLeCheval(cnx, idCheval);

            if (ch != null) {
                System.out.println("Cheval #" + idCheval + " : " + ch.getNom());
            } else {
                System.out.println("Aucun cheval trouvé pour id=" + idCheval);
            }

            // 3. Lister tous les chevaux
            ArrayList<Cheval> tousChevaux = DaoCheval.getLesChevaux(cnx);
            System.out.println("nombre de chevaux = " + tousChevaux.size());

            for (Cheval c : tousChevaux) {
                System.out.print("cheval : " + c.getId() + " " + c.getNom());

                // Race (si set par la DAO)
                if (c.getRace() != null) {
                    System.out.print(" | race=" + c.getRace().getNom());
                }

                // Sexe
                System.out.print(" | sexe=" + c.getSexe());

                // Date naissance
                System.out.print(" | né le=" + c.getDateNaissance());

                System.out.println();
            }

            // 4. Re-tester l'accès à un cheval précis avec détails
            ch = DaoCheval.getLeCheval(cnx, 3);
            if (ch != null) {
                System.out.println("Detail cheval #3 :");
                System.out.println("  id=" + ch.getId());
                System.out.println("  nom=" + ch.getNom());
                System.out.println("  sexe=" + ch.getSexe());
                System.out.println("  naissance=" + ch.getDateNaissance());
                System.out.println("  poids=" + ch.getPoids());
                System.out.println("  taille=" + ch.getTaille());
                if (ch.getRace() != null) {
                    System.out.println("  race=" + ch.getRace().getNom());
                }
            } else {
                System.out.println("Aucun cheval trouvé pour id=3");
            }

            // 5. Ventes
            ArrayList<Vente> ventes = DaoVente.getLesVentes(cnx);
            System.out.println("nombre de ventes = " + ventes.size());

            for (Vente v : ventes) {
                System.out.print("vente : " + v.getId() + " " + v.getNom());

                // On protège car v.getLieu() peut être null selon ta DAO
                if (v.getLieu() != null) {
                    System.out.print(" | lieu=" + v.getLieu().getVille());
                }

                if (v.getDateDeVente() != null) {
                    System.out.print(" | date=" + v.getDateDeVente());
                }

                System.out.println();
            }

        } catch (SQLException e) {
            // problème d'ouverture de la connexion ou SQL dans les DAO
            System.err.println("ERREUR SQL : " + e.getMessage());
            e.printStackTrace();

        } catch (Exception e) {
            // tout autre problème (NullPointerException, etc.)
            System.err.println("ERREUR GÉNÉRALE : " + e.getMessage());
            e.printStackTrace();

        } finally {
            // 6. Toujours fermer la connexion à la fin
            Connexionbdd.fermerConnexion(cnx);
        }
    }
}
