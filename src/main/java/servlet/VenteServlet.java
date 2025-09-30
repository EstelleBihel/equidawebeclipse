package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import database.DaoVente;
import model.Vente;
import model.Lot;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

@WebServlet(name = "venteServlet", value = "/vente-servlet/*")
public class VenteServlet extends HttpServlet {

    @Override
    public void init() {
        Connection cnx = (Connection) getServletContext().getAttribute("cnx");
        System.out.println("[INIT VenteServlet] cnx est null ? " + (cnx == null));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        // Connexion à CHAQUE requête
        Connection cnx = (Connection) getServletContext().getAttribute("cnx");
        if (cnx == null) {
            throw new ServletException("Connexion DB absente du contexte (attribut 'cnx').");
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
                } catch (NumberFormatException e) {
                    response.sendRedirect(request.getContextPath() + "/vente-servlet/list");
                }
            }

            case "/lots" -> {
                int idVente = Integer.parseInt(request.getParameter("idVente"));
                ArrayList<Lot> lesLots = DaoVente.getLesLots(cnx, idVente);
                request.setAttribute("pLesLots", lesLots);
                request.setAttribute("pIdVente", idVente);
                getServletContext()
                    .getRequestDispatcher("/WEB-INF/views/vente/lots.jsp")
                    .forward(request, response);
            }

            default -> response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    // Pas de /add ici car ton DaoVente n'expose pas encore d'insert.
    // Quand tu ajouteras DaoVente.ajouterVente(...), on branchera doPost.
}
