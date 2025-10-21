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

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext ctx = event.getServletContext();
        try {
            Class.forName("org.mariadb.jdbc.Driver");

            // On stocke seulement les paramètres
            ctx.setAttribute("db.url",  "jdbc:mariadb://127.0.0.1:3307/equida?useUnicode=true&characterEncoding=UTF-8");
            ctx.setAttribute("db.user", "root");
            ctx.setAttribute("db.pass", "");

            System.out.println("✅ Paramètres DB chargés (port 3307).");
        } catch (Exception e) {
            System.err.println("❌ Erreur init DB : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        System.out.println("Contexte détruit.");
        try {
            java.sql.Driver driver = DriverManager.getDriver("jdbc:mariadb://127.0.0.1:3307/equida");
            DriverManager.deregisterDriver(driver);
        } catch (SQLException ignore) {}
    }

    /** Getter pratique : renvoie une connexion OUVERTE ; rouvre si besoin. */
    public static Connection getConnection(ServletContext ctx) throws SQLException {
        Connection cnx = (Connection) ctx.getAttribute("db.cnx");
        if (cnx != null && !cnx.isClosed()) return cnx;

        String url  = (String) ctx.getAttribute("db.url");
        String user = (String) ctx.getAttribute("db.user");
        String pass = (String) ctx.getAttribute("db.pass");

        cnx = DriverManager.getConnection(url, user, pass);
        ctx.setAttribute("db.cnx", cnx);
        return cnx;
    }
}
