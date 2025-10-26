<%@ page import="database.Connexionbdd, java.sql.Connection" %>
<%
    Connection con = null;
    String status = "";
    String erreur = "";

    try {
        con = Connexionbdd.ouvrirConnexion(); // <-- ici on utilise OUVRIRConnexion
        if (con != null && !con.isClosed()) {
            status = "OK";
        } else {
            status = "FAILED (null ou fermée)";
        }
    } catch (Exception e) {
        status = "ERREUR";
        erreur = e.getMessage();
    } finally {
        Connexionbdd.fermerConnexion(con);
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Connection test</title>
</head>
<body>
    <h1>Test connexion BDD</h1>

    <p>Statut : <strong><%= status %></strong></p>

    <% if ("ERREUR".equals(status)) { %>
        <p>Exception : <%= erreur %></p>
    <% } %>

</body>
</html>
