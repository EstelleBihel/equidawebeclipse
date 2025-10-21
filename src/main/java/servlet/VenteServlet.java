package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import database.DaoVente;
import database.ConnexionServlet;  
import model.Vente;
import model.Lot;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "venteServlet", value = "/vente-servlet/*")
public class VenteServlet extends HttpServlet {

    // Plus besoin de stocker/afficher la connexion au init()
    @Override
    public void init() { /* no-op */ }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        // 🔐 Connexion vivante à chaque requête (réouvre si fermée)
        final Connection cnx;
        try {
            cnx = ConnexionServlet.getConnection(getServletContext());
        } catch (SQLException e) {
            throw new ServletException("Impossible d’obtenir une connexion DB", e);
        }

        String path = request.getPathInfo();
        if (path == null || path.isBlank()) path = "/list";

        switch (path) {
            case "/list" -> {
                ArrayList<Vente> lesVentes = DaoVente.getLesVentes(cnx);
                request.setAttribute("pLesVentes", lesVentes);
                getServletContext()
                        .getRequestDispatcher("/WEB-INF/views/vente/list.jsp")
                        .forward(request, response);
            }

            case "/show" -> {
                try {
                    int idVente = Integer.parseInt(request.getParameter("idVente"));
                    Vente laVente = DaoVente.getVente(cnx, idVente);
                    if (laVente != null) {
                        request.setAttribute("pLaVente", laVente);
                        getServletContext()
                                .getRequestDispatcher("/WEB-INF/views/vente/show.jsp")
                                .forward(request, response);
                    } else {
                        response.sendRedirect(request.getContextPath() + "/vente-servlet/list");
                    }
                } catch (NumberFormatException ex) {
                    response.sendRedirect(request.getContextPath() + "/vente-servlet/list");
                }
            }

            case "/lots" -> {
                try {
                    int idVente = Integer.parseInt(request.getParameter("idVente"));
                    ArrayList<Lot> lesLots = DaoVente.getLesLots(cnx, idVente);
                    request.setAttribute("pLesLots", lesLots);
                    request.setAttribute("pIdVente", idVente);
                    getServletContext()
                            .getRequestDispatcher("/WEB-INF/views/vente/lots.jsp")
                            .forward(request, response);
                } catch (NumberFormatException ex) {
                    response.sendRedirect(request.getContextPath() + "/vente-servlet/list");
                }
            }

            default -> response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
