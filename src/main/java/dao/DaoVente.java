package dao;

import model.Vente;
import model.Lieu;
import model.Lot;
import model.Cheval;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DaoVente {
    //Méthode getLesVentes = retourne une liste d’objets Vente à partir de la base de données.
    public static ArrayList<Vente> getLesVentes(Connection cnx) {
        ArrayList<Vente> lesVentes = new ArrayList<>();
        //Req SQL avec jointure pour récupérer les ventes et leurs lieux associés
        String sql = "SELECT v.id as v_id, v.nom as v_nom, v.date as v_date, " +
                "l.id as l_id, l.ville as l_ville, l.nb_de_boxes as l_nb_de_boxes, l.commentaires as l_commentaires " +
                "FROM vente v " +
                "INNER JOIN lieu l ON v.lieu_id = l.id";
        try (PreparedStatement requeteSql = cnx.prepareStatement(sql);
             ResultSet resultatRequete = requeteSql.executeQuery()) {
            // Parcours des résultats de la requête : pour chaque ligne, création d'un objet Vente et d'un objet Lieu, puis association
            while (resultatRequete.next()) {
                Vente vente = new Vente();
                vente.setId(resultatRequete.getInt("v_id"));
                vente.setNom(resultatRequete.getString("v_nom"));
                vente.setDateDeVente(resultatRequete.getString("v_date"));

                Lieu lieu = new Lieu();
                lieu.setId(resultatRequete.getInt("l_id"));
                lieu.setVille(resultatRequete.getString("l_ville"));
                lieu.setNbDeBoxes(resultatRequete.getInt("l_nb_de_boxes"));
                lieu.setCommentaires(resultatRequete.getString("l_commentaires"));

                vente.setLieu(lieu);
                lesVentes.add(vente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("La requête de getLesVentes a généré une exception SQL");
        }
        //retourne la liste des ventes avec leurs lieux associés
        return lesVentes;
    }
    
    public static ArrayList<Lot> getLesLots(Connection cnx, int idVente) {
        ArrayList<Lot> lesLots = new ArrayList<>();

        String sql = "SELECT "
                + "  l.id AS lot_id, "
                + "  l.prixDepart AS lot_prix_depart, "
                + "  c.id AS cheval_id, "
                + "  c.nom AS cheval_nom, "
                + "  cl.id AS client_id, "
                + "  cl.nom AS client_nom, "
                + "  cl.prenom AS client_prenom, "
                + "  cl.ville AS client_ville, "
                + "  cl.numeroRue AS client_numeroRue, "
                + "  cl.rue AS client_rue "               
                + "FROM lot l "
                + "LEFT JOIN cheval c ON l.cheval_id = c.id "
                + "LEFT JOIN client cl ON c.proprietaire_id = cl.id "
                + "WHERE l.vente_id = ?";

        try (PreparedStatement requeteSql = cnx.prepareStatement(sql)) {
            requeteSql.setInt(1, idVente);

            try (ResultSet rs = requeteSql.executeQuery()) {
                while (rs.next()) {
                    Lot lot = new Lot();
                    lot.setId(rs.getInt("lot_id"));
                    lot.setPrixDepart(rs.getInt("lot_prix_depart")); 

                    // Cheval lié 
                    int chevalId = rs.getInt("cheval_id");
                    if (!rs.wasNull()) {
                        model.Cheval ch = new model.Cheval();
                        ch.setId(chevalId);
                        ch.setNom(rs.getString("cheval_nom"));
                        lot.setCheval(ch);
                    }
                    // Propriétaire lié	
                    int clientId = rs.getInt("client_id");
                    if (!rs.wasNull()) {
						model.Client proprietaire = new model.Client();
						proprietaire.setId(clientId);
						proprietaire.setNom(rs.getString("client_nom"));
						proprietaire.setPrenom(rs.getString("client_prenom"));
						proprietaire.setVille(rs.getString("client_ville"));
						proprietaire.setNumeroRue(rs.getInt("client_numeroRue"));
						proprietaire.setRue(rs.getString("client_rue"));

						if (lot.getCheval() != null) {
							lot.getCheval().setProprietaire(proprietaire);
						}
					}
                
                    model.Vente v = new model.Vente();
                    v.setId(idVente);
                    lot.setVente(v);

                    lesLots.add(lot);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("La requête de getLesLots a généré une exception SQL");
        }

        return lesLots;
    }

   
    public static Vente getVente(Connection cnx, int idVente) {
        Vente vente = null;

        String sql = "SELECT " +
                     "  v.id    AS v_id, " +
                     "  v.nom   AS v_nom, " +
                     "  v.date  AS v_date, " +
                     "  l.id    AS l_id, " +
                     "  l.ville AS l_ville, " +
                     "  l.nb_de_boxes AS l_nb_de_boxes, " +
                     "  l.commentaires AS l_commentaires " +
                     "FROM vente v " +
                     "INNER JOIN lieu l ON v.lieu_id = l.id " +
                     "WHERE v.id = ?";

        try (PreparedStatement requeteSql = cnx.prepareStatement(sql)) {
            requeteSql.setInt(1, idVente);

            try (ResultSet rs = requeteSql.executeQuery()) {
                if (rs.next()) {
                    vente = new Vente();
                    vente.setId(rs.getInt("v_id"));
                    vente.setNom(rs.getString("v_nom"));
                    vente.setDateDeVente(rs.getString("v_date"));

                    Lieu lieu = new Lieu();
                    lieu.setId(rs.getInt("l_id"));
                    lieu.setVille(rs.getString("l_ville"));
                    lieu.setNbDeBoxes(rs.getInt("l_nb_de_boxes"));
                    lieu.setCommentaires(rs.getString("l_commentaires"));

                    vente.setLieu(lieu);

               	 // Récupérer et associer les lots à la vente
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("La requête de getVente a généré une exception SQL");
        }

        return vente;
    }
    

	public static Vente getLaVente(Connection cnx, int idVente) {

		return null;
	}

	public static ArrayList<Lot> getLotsByVenteId(Connection cnx, int idVente) {

		return null;	

	}
    
	public static ArrayList<Lot> getLesLotsDeVente(Connection cnx, int idVente) {
	    ArrayList<Lot> lesLots = new ArrayList<>();

	    try {
	        String sql = "SELECT l.id AS lot_id, l.prix_depart, c.id AS cheval_id, c.nom AS cheval_nom " +
	                     "FROM lot l " +
	                     "LEFT JOIN cheval c ON l.cheval_id = c.id " +
	                     "WHERE l.vente_id = ?";

	        PreparedStatement pstmt = cnx.prepareStatement(sql);
	        pstmt.setInt(1, idVente);
	        ResultSet rs = pstmt.executeQuery();

	        while (rs.next()) {
	            Lot unLot = new Lot();
	            unLot.setId(rs.getInt("lot_id"));
	            unLot.setPrixDepart(rs.getFloat("prix_depart"));

	            Cheval leCheval = new Cheval();
	            leCheval.setId(rs.getInt("cheval_id"));
	            leCheval.setNom(rs.getString("cheval_nom"));
	            unLot.setCheval(leCheval);

	            lesLots.add(unLot);
	        }

	        rs.close();
	        pstmt.close();

	    } catch (SQLException e) {
	        System.out.println("Erreur DaoVente.getLesLotsDeVente : " + e.getMessage());
	    }

	    return lesLots;
	}

}
