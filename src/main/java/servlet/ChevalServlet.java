package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.DaoCheval;
import dao.DaoRace;
import database.Connexionbdd;
import model.Cheval;
import model.Race;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

// Servlet gérant les opérations liées aux chevaux
public class ChevalServlet extends HttpServlet {

    private Connection cnx;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            cnx = Connexionbdd.ouvrirConnexion();
            if (cnx == null) {
                throw new ServletException("Echec ouverture BDD (cnx == null)");
            }
            System.out.println(">>> ChevalServlet.init : connexion OK = " + cnx);
        } catch (SQLException e) {
            throw new ServletException("Impossible d'ouvrir la connexion BDD dans ChevalServlet.init()", e);
        }
    }

    @Override
    public void destroy() {
        Connexionbdd.fermerConnexion(cnx);
        System.out.println(">>> ChevalServlet.destroy : connexion fermée.");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String path = request.getPathInfo();

        if (path == null || "/".equals(path)) {
            response.sendRedirect(request.getContextPath() + "/cheval-servlet/list");
            return;
        }

        switch (path) {
        	// /cheval-servlet/list
            case "/list": {
                ArrayList<Cheval> lesChevaux = DaoCheval.getLesChevaux(cnx);
                request.setAttribute("pLesChevaux", lesChevaux);

                this.getServletContext()
                        .getRequestDispatcher("/WEB-INF/views/cheval/list.jsp")
                        .forward(request, response);
                break;
            }
            // /cheval-servlet/show
            case "/show": {
                try {
                    int idCheval = Integer.parseInt(request.getParameter("idCheval"));
                    Cheval leCheval = DaoCheval.getLeCheval(cnx, idCheval);

                    if (leCheval != null) {
                        request.setAttribute("pLeCheval", leCheval);
                        this.getServletContext()
                                .getRequestDispatcher("/WEB-INF/views/cheval/show.jsp")
                                .forward(request, response);
                    } else {
                        response.sendRedirect(request.getContextPath() + "/cheval-servlet/list");
                    }
                } catch (NumberFormatException e) {
                    response.sendRedirect(request.getContextPath() + "/cheval-servlet/list");
                }
                break;
            }
			// /cheval-servlet/add
            case "/add": {
                ArrayList<Race> lesRaces = DaoRace.getLesRaces(cnx);
                request.setAttribute("pLesRaces", lesRaces);

                this.getServletContext()
                        .getRequestDispatcher("/WEB-INF/views/cheval/add.jsp")
                        .forward(request, response);
                break;
            }

            default: {
                response.sendRedirect(request.getContextPath() + "/cheval-servlet/list");
                break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getPathInfo();
        if (path == null) {
            response.sendRedirect(request.getContextPath() + "/cheval-servlet/list");
            return;
        }
        
        switch (path) {
            case "/add": {
                try {
                    String nom = request.getParameter("nom");
                    String dateNaissanceStr = request.getParameter("dateNaissance");
                    int raceId = Integer.parseInt(request.getParameter("race"));

                    Cheval nouveauCheval = new Cheval();
                    nouveauCheval.setNom(nom);

                    if (dateNaissanceStr != null && !dateNaissanceStr.isEmpty()) {
                        LocalDate dateNaissance = LocalDate.parse(dateNaissanceStr);
                        nouveauCheval.setDateNaissance(dateNaissance);
                    }

                    Race race = DaoRace.getRaceById(cnx, raceId);
                    if (race != null) {
                        nouveauCheval.setRace(race);
                    } else {
                        throw new Exception("La race sélectionnée n'existe pas");
                    }

                    boolean ok = DaoCheval.ajouterCheval(cnx, nouveauCheval);
                    if (ok) {
                        response.sendRedirect(
                                request.getContextPath()
                                        + "/cheval-servlet/show?idCheval="
                                        + nouveauCheval.getId()
                        );
                    } else {
                        throw new Exception("Erreur lors de l'enregistrement du cheval");
                    }

                } catch (NumberFormatException e) {
                    request.setAttribute("message", "Race invalide");
                    request.setAttribute("pLesRaces", DaoRace.getLesRaces(cnx));
                    this.getServletContext()
                            .getRequestDispatcher("/WEB-INF/views/cheval/add.jsp")
                            .forward(request, response);

                } catch (Exception e) {
                    request.setAttribute("message", "Erreur : " + e.getMessage());
                    request.setAttribute("pLesRaces", DaoRace.getLesRaces(cnx));
                    this.getServletContext()
                            .getRequestDispatcher("/WEB-INF/views/cheval/add.jsp")
                            .forward(request, response);
                }
                break;
            }

            default: {
                response.sendRedirect(request.getContextPath() + "/cheval-servlet/list");
                break;
            }
        }
    }
}
