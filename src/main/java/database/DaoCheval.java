package database;

import model.Cheval;
import model.Race;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DaoCheval {

    // === SQL ===
    private static final String SQL_LISTE = """
        SELECT c.id AS c_id, c.nom AS c_nom, c.date_naissance AS c_date,
               r.id AS r_id, r.nom AS r_nom
        FROM cheval c
        INNER JOIN race r ON c.race_id = r.id
        ORDER BY c.id
        """;

    private static final String SQL_UN = """
        SELECT c.id AS c_id, c.nom AS c_nom, c.date_naissance AS c_date,
               r.id AS r_id, r.nom AS r_nom
        FROM cheval c
        INNER JOIN race r ON c.race_id = r.id
        WHERE c.id = ?
        """;

    private static final String SQL_INSERT = """
        INSERT INTO cheval (nom, date_naissance, race_id)
        VALUES (?, ?, ?)
        """;

    /**
     * Récupère tous les chevaux avec leur race.
     */
    public static ArrayList<Cheval> getLesChevaux(Connection cnx) {
        if (cnx == null) throw new IllegalArgumentException("Connexion nulle (cnx).");

        ArrayList<Cheval> lesChevaux = new ArrayList<>();
        try (PreparedStatement ps = cnx.prepareStatement(SQL_LISTE);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Cheval c = mapCheval(rs);
                lesChevaux.add(c);
            }
        } catch (SQLException e) {
            System.err.println("[DaoCheval] getLesChevaux - SQL error: " + e.getMessage());
            e.printStackTrace();
        }
        return lesChevaux;
    }

    /**
     * Récupère un cheval par son identifiant
     */
    public static Cheval getLeCheval(Connection cnx, int idCheval) {
        if (cnx == null) throw new IllegalArgumentException("Connexion nulle (cnx).");

        Cheval cheval = null;
        try (PreparedStatement ps = cnx.prepareStatement(SQL_UN)) {
            ps.setInt(1, idCheval);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cheval = mapCheval(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("[DaoCheval] getLeCheval - SQL error: " + e.getMessage());
            e.printStackTrace();
        }
        return cheval;
    }

    /**
     * Ajoute un nouveau cheval dans la base de données.
     */
    public static boolean ajouterCheval(Connection cnx, Cheval cheval) {
        if (cnx == null) throw new IllegalArgumentException("Connexion nulle (cnx).");
        if (cheval == null) throw new IllegalArgumentException("Cheval null.");
        if (cheval.getRace() == null)
            throw new IllegalArgumentException("Race du cheval manquante.");

        try (PreparedStatement ps = cnx.prepareStatement(
                SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // nom
            ps.setString(1, cheval.getNom());

            // date_naissance (nullable)
            if (cheval.getDateNaissance() != null) {
                ps.setDate(2, java.sql.Date.valueOf(cheval.getDateNaissance()));
            } else {
                ps.setNull(2, java.sql.Types.DATE);
            }

            // race_id
            ps.setInt(3, cheval.getRace().getId());

            int updated = ps.executeUpdate();
            if (updated == 1) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        cheval.setId(keys.getInt(1));
                    }
                }
                return true;
            }
            return false;

        } catch (SQLException e) {
            System.err.println("[DaoCheval] ajouterCheval - SQL error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    private static Cheval mapCheval(ResultSet rs) throws SQLException {
        Cheval c = new Cheval();
        c.setId(rs.getInt("c_id"));
        c.setNom(rs.getString("c_nom"));

        // date_naissance (nullable)
        java.sql.Date d = rs.getDate("c_date");
        if (d != null) {
            c.setDateNaissance(d.toLocalDate());
        }

        Race r = new Race();
        r.setId(rs.getInt("r_id"));
        r.setNom(rs.getString("r_nom"));
        c.setRace(r);

        return c;
    }
}
