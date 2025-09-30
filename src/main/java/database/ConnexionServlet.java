package database;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebListener
public class ConnexionServlet implements ServletContextListener {

    private Connection cnx = null;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext ctx = event.getServletContext();
        try {
            // ====== CHOIX DU DRIVER ======
            // Par défaut on suppose MariaDB (classique avec XAMPP/WAMP modernes).
            // Si tu utilises MySQL-Connector plutôt que MariaDB, commente MariaDB et décommente MySQL plus bas.

            // --- MariaDB (RECOMMANDÉ si tu as le jar mariadb-java-client) ---
            Class.forName("org.mariadb.jdbc.Driver");
            String url  = "jdbc:mariadb://127.0.0.1:3307/equida?useUnicode=true&characterEncoding=UTF-8";
            String user = "root";
            String pass = ""; // mets ton mot de passe si tu en as un
            cnx = DriverManager.getConnection(url, user, pass);

            // --- MySQL (ALTERNATIVE si tu as mysql-connector-j) ---
            /*
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url  = "jdbc:mysql://127.0.0.1:3307/equida?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false";
            String user = "root";
            String pass = "";
            cnx = DriverManager.getConnection(url, user, pass);
            */

            ctx.setAttribute("cnx", cnx);
            System.out.println("[Listener] Attributs présents après init :");
            var e = ctx.getAttributeNames();
            while (e.hasMoreElements()) {
                System.out.println(" - " + e.nextElement());
            }

            System.out.println("✅ Connexion DB ouverte (port 3307) et stockée dans le contexte.");
        } catch (Exception e) {
            System.err.println("❌ Erreur d'initialisation connexion DB : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        System.out.println("----------- Contexte détruit -----------");
        if (cnx != null) {
            try {
                cnx.close();
                System.out.println("Connexion fermée");
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
        }

        // Désenregistrement du driver JDBC pour éviter le warning Tomcat
        try {
            java.sql.Driver driver = DriverManager.getDriver("jdbc:mariadb://127.0.0.1:3307/equida");
            DriverManager.deregisterDriver(driver);
            System.out.println("Driver JDBC MariaDB désenregistré.");
        } catch (SQLException e) {
            System.err.println("Erreur lors du désenregistrement du driver : " + e.getMessage());
        }
    }
}
