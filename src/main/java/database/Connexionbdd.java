package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexionbdd {
	// Méthode pour ouvrir une connexion à la base de données MariaDB
    public static Connection ouvrirConnexion() throws SQLException {
        try {
            // Charge le driver MariaDB
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MariaDB introuvable.", e);
        }

        // Détails de la connexion
        String url = "jdbc:mariadb://localhost:3307/equida";
        String user = "root";
        String password = ""; 

        Connection cnx = DriverManager.getConnection(url, user, password);
        System.out.println(">>> Connexion BDD ouverte avec succès sur " + url);
        return cnx;
    }
	// Méthode pour fermer une connexion à la base de données
    public static void fermerConnexion(Connection cnx) {
        if (cnx != null) {
            try {
                cnx.close();
                System.out.println(">>> Connexion BDD fermée proprement.");
            } catch (SQLException e) {
                System.err.println(">>> Erreur fermeture connexion : " + e.getMessage());
            }
        }
    }
}
