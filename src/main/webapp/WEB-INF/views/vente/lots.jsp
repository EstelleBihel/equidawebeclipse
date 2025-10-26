<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.Lot" %>
<%@ page import="model.Cheval" %>
<%@ page import="java.util.ArrayList" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lots de la vente</title>

    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          crossorigin="anonymous">
    <style>
        body {
            padding-top: 50px;
        }
        .special {
            padding-top: 50px;
        }
        .header-actions {
            margin-bottom: 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .section-title {
            margin-top:30px;
            font-size:16px;
            font-weight:bold;
            color:#001A66;
        }
    </style>
</head>
<body>

<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <a href="<%= request.getContextPath() %>/vente-servlet/list" class="navbar-brand">
                Ventes de chevaux
            </a>
        </div>
    </div>
</nav>

<div class="container special">

    <div class="header-actions">
        <h2 class="h2">Lots de la vente</h2>
        <!-- si tu veux un bouton retour/ajout tu peux en mettre ici -->
        <a href="<%= request.getContextPath() %>/vente-servlet/list" class="btn btn-default">
            ← Retour aux ventes
        </a>
    </div>

    <%
        // On récupère la liste de lots envoyée par la servlet
        ArrayList<Lot> lesLots = (ArrayList<Lot>) request.getAttribute("pLesLots");
    %>

    <div class="table-responsive">
        <table class="table table-bordered table-striped table-sm">
            <thead>
            <tr>
                <th>N° lot</th>
                <th>Nom du cheval</th>
                <th>Sexe</th>
                <th>Race / Type</th>
                <th>Vendeur</th>
                <th>Prix départ (€)</th>
            </tr>
            </thead>
            <tbody>

            <%
                if (lesLots != null && !lesLots.isEmpty()) {

                    for (Lot lot : lesLots) {

                        Cheval ch = lot.getCheval(); // supposé exister dans ta classe Lot

                        // Sécurité anti-null pour éviter les NullPointerException
                        String chevalNom = (ch != null && ch.getNom() != null) ? ch.getNom() : "—";
                        String chevalSexe = (ch != null && ch.getSexe() != null) ? ch.getSexe() : "—";

                        String chevalRace = "—";
                        if (ch != null && ch.getRace() != null && ch.getRace().getNom() != null) {
                            chevalRace = ch.getRace().getNom();
                        }

                        String vendeur = "—";
                        if (ch != null && ch.getProprietaire() != null) {
                            String nomVend = (ch.getProprietaire().getNom() != null) ? ch.getProprietaire().getNom() : "";
                            String prenomVend = (ch.getProprietaire().getPrenom() != null) ? ch.getProprietaire().getPrenom() : "";
                            vendeur = nomVend + " " + prenomVend;
                        }

                     // prix de départ
                        String prixDepart = String.valueOf(lot.getPrixDepart());

            %>

            <tr>
                <td><%= lot.getId() %></td>
                <td><%= chevalNom %></td>
                <td><%= chevalSexe %></td>
                <td><%= chevalRace %></td>
                <td><%= vendeur %></td>
                <td><%= prixDepart %></td>
            </tr>

            <%
                    } // fin for
                } else {
            %>

            <tr>
                <td colspan="6" class="text-center">
                    Aucun lot trouvé pour cette vente.
                </td>
            </tr>

            <%
                } // fin if
            %>

            </tbody>
        </table>
    </div>

</div>

<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>
