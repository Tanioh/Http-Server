<?php 
session_start();
$_SESSION["gaga"] = 11;
?>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Formulaire d'Inscription</title>
</head>
<body>
    <form action="/traitement.php" method="post" enctype="application/x-www-form-urlencoded">
        <label for="nom">Nom :</label>
        <input type="text" id="nom" name="nom" required><br><br>
        
        <label for="age">Âge :</label>
        <input type="number" id="age" name="age" required><br><br>
        
        <input type="submit" value="Envoyer">
    </form>
</body>
</html>