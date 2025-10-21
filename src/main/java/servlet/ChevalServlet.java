package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import database.DaoCheval;
import database.DaoRace;
import model.Cheval;
import model.Race;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;

@WebServlet(name = "chevalServlet", value = "/cheval-servlet/*")
public class ChevalServlet extends HttpServlet {

    @Override
    public void init() {
        // Log simple : vérifie que la connexion est bien déposée par le listener
        Connection cnx = (Connection) getServletContext().getAttribute("cnx");
        System.out.println("[INIT ChevalServlet] cnx est null ? " + (cnx == null));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        // 1) Connexion à CHAQUE requête
        Connection cnx = (Connection) getServletContext().getAttribute("cnx");
        if (cnx == null) {
            throw new ServletException("Connexion DB absente du contexte (attribut 'cnx').");
        }

        // 2) Normalisation du path => liés aux .jsp dans /WEB-INF/views/cheval/
        String path = request.getPathInfo();
        if (path == null || path.isBlank()) path = "/list";

        switch (path) {
            case "/list" -> {
                ArrayList<Cheval> lesChevaux = DaoCheval.getLesChevaux(cnx);
                request.setAttribute("pLesChevaux", lesChevaux);
                getServletContext()
                        .getRequestDispatcher("/WEB-INF/views/cheval/list.jsp")
                        .forward(request, response);
            }

            case "/show" -> {
                try {
                    String idParam = request.getParameter("idCheval");
                    int idCheval = Integer.parseInt(idParam);
                    Cheval leCheval = DaoCheval.getLeCheval(cnx, idCheval);
                    if (leCheval != null) {
                        request.setAttribute("pLeCheval", leCheval);
                        getServletContext()
                                .getRequestDispatcher("/WEB-INF/views/cheval/show.jsp")
                                .forward(request, response);
                    } else {
                        // Si l'id n'existe pas → retour liste
                        response.sendRedirect(request.getContextPath() + "/cheval-servlet/list");
                    }
                } catch (NumberFormatException e) {
                    // idCheval manquant ou invalide
                    response.sendRedirect(request.getContextPath() + "/cheval-servlet/list");
                }
            }

            case "/add" -> {
                ArrayList<Race> lesRaces = DaoRace.getLesRaces(cnx);
                request.setAttribute("pLesRaces", lesRaces);
                getServletContext()
                        .getRequestDispatcher("/WEB-INF/views/cheval/add.jsp")
                        .forward(request, response);
            }

            default -> response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Connection cnx = (Connection) getServletContext().getAttribute("cnx");
        if (cnx == null) {
            throw new ServletException("Connexion DB absente du contexte (attribut 'cnx').");
        }

        String path = request.getPathInfo();
        if (path == null) path = "";

        if ("/add".equals(path)) {
            try {
                // Récupération des champs du formulaire
                String nom = request.getParameter("nom");
                String dateNaissanceStr = request.getParameter("dateNaissance");
                String raceIdStr = request.getParameter("race");

                // Validation basique
                if (nom == null || nom.isBlank() || raceIdStr == null || raceIdStr.isBlank()) {
                    throw new IllegalArgumentException("Nom et race sont obligatoires.");
                }

                int raceId = Integer.parseInt(raceIdStr);

                // Construction de l'entité
                Cheval nouveauCheval = new Cheval();
                nouveauCheval.setNom(nom);

                if (dateNaissanceStr != null && !dateNaissanceStr.isBlank()) {
                    // Attendu au format ISO (yyyy-MM-dd) si input type="date"
                    LocalDate dateNaissance = LocalDate.parse(dateNaissanceStr);
                    nouveauCheval.setDateNaissance(dateNaissance);
                }

                // Récupération de la race
                Race race = DaoRace.getRaceById(cnx, raceId);
                if (race == null) {
                    throw new IllegalArgumentException("La race sélectionnée n'existe pas.");
                }
                nouveauCheval.setRace(race);

                // Insertion
                boolean ok = DaoCheval.ajouterCheval(cnx, nouveauCheval);
                if (!ok) {
                    throw new RuntimeException("Erreur lors de l'enregistrement du cheval.");
                }

                // Redirection vers la page détail du nouveau cheval
                response.sendRedirect(request.getContextPath()
                        + "/cheval-servlet/show?idCheval=" + nouveauCheval.getId());

            } catch (NumberFormatException e) {
                request.setAttribute("message", "Erreur : la race sélectionnée n'est pas valide.");
                request.setAttribute("pLesRaces", DaoRace.getLesRaces(cnx));
                getServletContext()
                        .getRequestDispatcher("/WEB-INF/views/cheval/add.jsp")
                        .forward(request, response);

            } catch (Exception e) {
                request.setAttribute("message", "Erreur : " + e.getMessage());
                request.setAttribute("pLesRaces", DaoRace.getLesRaces(cnx));
                getServletContext()
                        .getRequestDispatcher("/WEB-INF/views/cheval/add.jsp")
                        .forward(request, response);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
