<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.Vente, model.Lieu" %>
<%@ page import="java.util.ArrayList, java.util.List" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Equida - Ventes</title>
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
          crossorigin="anonymous">
    <style>
        body { padding-top: 50px; }
        .special { padding-top: 50px; }
        .header-actions {
            margin-bottom: 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .table td, .table th { vertical-align: middle !important; }
    </style>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="<%= request.getContextPath() %>/vente-servlet/list">
                Gestion des ventes
            </a>
        </div>
    </div>
</nav>

<div class="container special">
    <div class="header-actions">
        <h2 class="h2">Liste des ventes</h2>
        <a href="<%= request.getContextPath() %>/vente-servlet/add" class="btn btn-primary">
            <span class="glyphicon glyphicon-plus"></span> Ajouter une vente
        </a>
    </div>

    <div class="table-responsive">
        <%
            List<Vente> lesVentes = (List<Vente>) request.getAttribute("pLesVentes");
            if (lesVentes == null) lesVentes = new ArrayList<>();
        %>
        <table class="table table-striped table-sm">
            <thead>
            <tr>
                <th>ID</th>
                <th>Nom</th>
                <th>Date</th>
                <th>Ville</th>
                <th>Boxes</th>
                <th>Commentaires</th>
                <th style="width:160px;">Actions</th>
            </tr>
            </thead>
            <tbody>
            <%
                if (lesVentes.isEmpty()) {
            %>
                <tr><td colspan="7" class="text-muted">Aucune vente trouvée.</td></tr>
            <%
                } else {
                    for (Vente v : lesVentes) {
                        Lieu l = v.getLieu();
            %>
                <tr>
                    <td><%= v.getId() %></td>
                    <td>
                        <a href="<%= request.getContextPath() %>/vente-servlet/show?idVente=<%= v.getId() %>">
                            <%= v.getNom() %>
                        </a>
                    </td>
                    <td><%= v.getDateDeVente() != null ? v.getDateDeVente() : "" %></td>
                    <td><%= l != null ? l.getVille() : "" %></td>
                    <td><%= l != null ? l.getNbDeBoxes() : "" %></td>
                    <td><%= l != null ? l.getCommentaires() : "" %></td>
                    <td>
                        <a class="btn btn-xs btn-default"
                           href="<%= request.getContextPath() %>/vente-servlet/show?idVente=<%= v.getId() %>">
                            <span class="glyphicon glyphicon-eye-open"></span> Détails
                        </a>
                        <a class="btn btn-xs btn-info"
                           href="<%= request.getContextPath() %>/vente-servlet/lots?idVente=<%= v.getId() %>">
                            <span class="glyphicon glyphicon-th-list"></span> Lots
                        </a>
                    </td>
                </tr>
            <%
                    }
                }
            %>
            </tbody>
        </table>
    </div>
</div>

<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>
