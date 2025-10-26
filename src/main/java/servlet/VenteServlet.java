package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.DaoVente;
import database.Connexionbdd;
import model.Lot;
import model.Vente;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class VenteServlet extends HttpServlet {

    // Connexion propre à cette servlet
    private Connection cnx;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            cnx = Connexionbdd.ouvrirConnexion();
            if (cnx == null) {
                throw new ServletException("Echec ouverture BDD (cnx == null)");
            }
            System.out.println(">>> VenteServlet.init : connexion OK = " + cnx);
        } catch (SQLException e) {
            throw new ServletException("Impossible d'ouvrir la connexion BDD dans VenteServlet.init()", e);
        }
    }

    @Override
    public void destroy() {
        // 🔴 NOUVEAU : on ferme proprement la connexion quand la servlet est détruite
        Connexionbdd.fermerConnexion(cnx);
        System.out.println(">>> VenteServlet.destroy : connexion fermée.");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String path = request.getPathInfo();

        // Redirection vers la liste par défaut
        if (path == null || "/".equals(path)) {
            response.sendRedirect(request.getContextPath() + "/vente-servlet/list");
            return;
        }

        switch (path) {

            // /vente-servlet/list
            case "/list": {
                ArrayList<Vente> lesVentes = DaoVente.getLesVentes(cnx);

                request.setAttribute("pLesVentes", lesVentes);

                this.getServletContext()
                        .getRequestDispatcher("/WEB-INF/views/vente/list.jsp")
                        .forward(request, response);
                break;
            }

            // /vente-servlet/lots?idVente=1
            case "/lots": {
                try {
                    int idVente = Integer.parseInt(request.getParameter("idVente"));

                    Vente laVente = DaoVente.getLaVente(cnx, idVente);
                    ArrayList<Lot> lesLots = DaoVente.getLesLotsDeVente(cnx, idVente);

                    request.setAttribute("pLaVente", laVente);
                    request.setAttribute("pLesLots", lesLots);

                    this.getServletContext()
                            .getRequestDispatcher("/WEB-INF/views/vente/lots.jsp")
                            .forward(request, response);

                } catch (NumberFormatException e) {
                    // idVente pas valide -> retour liste
                    response.sendRedirect(request.getContextPath() + "/vente-servlet/list");
                }
                break;
            }

            default: {
                response.sendRedirect(request.getContextPath() + "/vente-servlet/list");
                break;
            }
        }
    }
}
