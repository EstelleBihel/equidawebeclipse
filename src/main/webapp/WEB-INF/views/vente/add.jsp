<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.CategorieVente, model.Lieu" %>
<%@ page import="java.util.ArrayList" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Equida - Ajouter une vente</title>
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
          crossorigin="anonymous">
    <style>
        body { padding-top: 50px; }
        .special { padding-top: 50px; }
        .form-container {
            background-color: #f8f9fa;
            border-radius: 5px;
            padding: 20px;
            margin-top: 20px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
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
    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <div class="form-container">
                <h2>Ajouter une nouvelle vente</h2>

                <form class="form-horizontal" action="<%= request.getContextPath() %>/vente-servlet/add" method="POST">

                    <!-- Nom de la vente -->
                    <div class="form-group">
                        <label for="nom" class="col-sm-3 control-label">Nom *</label>
                        <div class="col-sm-9">
                            <input type="text" name="nom" id="nom" class="form-control" required>
                        </div>
                    </div>

                    <!-- Date de la vente -->
                    <div class="form-group">
                        <label for="dateVente" class="col-sm-3 control-label">Date</label>
                        <div class="col-sm-9">
                            <input type="date" name="dateVente" id="dateVente" class="form-control">
                        </div>
                    </div>

                    <!-- Catégorie de vente -->
                    <div class="form-group">
                        <label for="categorie" class="col-sm-3 control-label">Catégorie *</label>
                        <div class="col-sm-9">
                            <select name="categorie" id="categorie" class="form-control" required>
                                <option value="">Sélectionnez une catégorie</option>
                                <%
                                    ArrayList<CategorieVente> lesCategories =
                                        (ArrayList<CategorieVente>) request.getAttribute("pLesCategories");
                                    if (lesCategories != null) {
                                        for (CategorieVente c : lesCategories) {
                                %>
                                    <option value="<%= c.getCode() %>"><%= c.getLibelle() %></option>
                                <%
                                        }
                                    }
                                %>
                            </select>
                        </div>
                    </div>

                    <!-- Lieu -->
                    <div class="form-group">
                        <label for="lieu" class="col-sm-3 control-label">Lieu *</label>
                        <div class="col-sm-9">
                            <select name="lieu" id="lieu" class="form-control" required>
                                <option value="">Sélectionnez un lieu</option>
                                <%
                                    ArrayList<Lieu> lesLieux =
                                        (ArrayList<Lieu>) request.getAttribute("pLesLieux");
                                    if (lesLieux != null) {
                                        for (Lieu l : lesLieux) {
                                %>
                                    <option value="<%= l.getId() %>">
                                        <%= l.getVille() %> (boxes: <%= l.getNbDeBoxes() %>)
                                    </option>
                                <%
                                        }
                                    }
                                %>
                            </select>
                        </div>
                    </div>

                    <!-- Boutons -->
                    <div class="form-group">
                        <div class="col-sm-offset-3 col-sm-9">
                            <button type="submit" class="btn btn-primary">
                                <span class="glyphicon glyphicon-plus"></span> Ajouter
                            </button>
                            <a href="<%= request.getContextPath() %>/vente-servlet/list" class="btn btn-default">
                                <span class="glyphicon glyphicon-remove"></span> Annuler
                            </a>
                        </div>
                    </div>

                    <!-- Message d'erreur -->
                    <%
                        Object msg = request.getAttribute("message");
                        if (msg != null) {
                    %>
                        <div class="alert alert-danger"><%= msg %></div>
                    <% } %>
                </form>
            </div>
        </div>
    </div>
</div>

<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>
