<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.Lot, model.Cheval, java.util.List, java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Lots de la vente</title>
  <link rel="stylesheet"
        href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <style>body{padding-top:50px}.special{padding-top:50px}</style>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top">
  <div class="container"><div class="navbar-header">
    <a class="navbar-brand" href="<%=request.getContextPath()%>/vente-servlet/list">Gestion des ventes</a>
  </div></div>
</nav>

<div class="container special">
  <h2>Lots de la vente #<%= request.getAttribute("pIdVente") %></h2>
  <%
    List<Lot> lots = (List<Lot>) request.getAttribute("pLesLots");
    if (lots == null) lots = new ArrayList<>();
  %>
  <table class="table table-striped">
    <thead><tr><th>ID</th><th>Cheval</th><th>Prix de départ</th></tr></thead>
    <tbody>
    <%
      if (lots.isEmpty()) {
    %>
      <tr><td colspan="3" class="text-muted">Aucun lot pour cette vente.</td></tr>
    <%
      } else {
        for (Lot lt : lots) {
          Cheval ch = lt.getCheval();
    %>
      <tr>
        <td><%= lt.getId() %></td>
        <td>
          <%
            if (ch != null) {
          %>
            <a href="<%= request.getContextPath() %>/cheval-servlet/show?idCheval=<%= ch.getId() %>">
              <%= ch.getNom() %>
            </a>
          <%
            } else {
          %>
            (Cheval non renseigné)
          <%
            }
          %>
        </td>
        <td><%= lt.getPrixDepart() %></td>
      </tr>
    <%
        }
      }
    %>
    </tbody>
  </table>

  <a href="<%=request.getContextPath()%>/vente-servlet/list" class="btn btn-default">
    <span class="glyphicon glyphicon-arrow-left"></span> Retour aux ventes
  </a>
</div>
</body>
</html>
