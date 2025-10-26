<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Equida - Accueil</title>
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          crossorigin="anonymous">
    <style>
        body { padding: 40px; font-family: Arial, sans-serif; }
        .box { max-width: 500px; margin: 0 auto; }
        h1 { font-size: 22px; margin-bottom: 20px; }
        .list-group a { font-size: 15px; }
    </style>
</head>
<body>

<div class="box">
    <h1>Equida - Accueil</h1>

    <p>Application déployée. Choisissez :</p>

    <div class="list-group">
        <a class="list-group-item" href="cheval-servlet/list">
            🐴 Liste des chevaux
        </a>

        <a class="list-group-item" href="vente-servlet/list">
            💸 Liste des ventes
        </a>
    </div>

    <hr>
    <p style="font-size:12px;color:#666;">
        Si ces liens donnent une erreur 404 ou 500, dis-le moi.
    </p>
</div>

</body>
</html>
