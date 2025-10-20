<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.Lot, model.Cheval, java.util.List, java.util.ArrayList" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Lots de la vente</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f7f7f7;
            margin: 40px;
        }
        h1 {
            color: #333;
            text-align: center;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 25px;
            background: #fff;
            box-shadow: 0 0 8px rgba(0,0,0,0.1);
        }
        th, td {
            padding: 10px;
            border: 1px solid #ddd;
            text-align: center;
        }
        th {
            background-color: #f0f0f0;
            color: #333;
        }
        tr:nth-child(even) {
            background-color: #fafafa;
        }
        a {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 15px;
            background: #337ab7;
            color: white;
            text-decoration: none;
            border-radius: 5px;
        }
        a:hover {
            background: #286090;
        }
    </style>
</head>
<body>

<h1>Lots de la vente n°${param.idVente}</h1>

<c:choose>
    <c:when test="${empty pLesLots}">
        <p style="text-align:center;">Aucun lot trouvé pour cette vente.</p>
    </c:when>
    <c:otherwise>
        <table>
            <thead>
                <tr>
                    <th>ID Lot</th>
                    <th>Cheval</th>
                    <th>Prix de départ</th>
                    <th>Vendeur</th>
                    <th>Adresse</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="lot" items="${pLesLots}">
                    <tr>
                        <td>${lot.id}</td>
                        <td>${lot.cheval != null ? lot.cheval.nom : ''}</td>
                        <td>${lot.prixDepart}</td>

                        <c:choose>
                            <c:when test="${lot.cheval != null && lot.cheval.proprietaire != null}">
                                <td>
                                    ${lot.cheval.proprietaire.nom} ${lot.cheval.proprietaire.prenom}
                                </td>
                                <td>
                                    ${lot.cheval.proprietaire.numeroRue}${lot.cheval.proprietaire.rue}<br/>
                                    ${lot.cheval.proprietaire.ville}
                                </td>                           
                            </c:when>
                            <c:otherwise>
                                <td colspan="4">— Aucun client enregistré —</td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:otherwise>
</c:choose>

<div style="text-align:center;">
    <a href="${pageContext.request.contextPath}/vente-servlet/list">← Retour à la liste des ventes</a>
</div>

</body>
</html>