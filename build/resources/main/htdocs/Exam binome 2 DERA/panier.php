<?php
session_start();
include("data.php");
?>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="style.css">
    <title>MON Panier</title>
</head>

<body>
    <h1>LISTE DES PRODUITS DANS LE PANIER</h1>
    <div class="contain-produit">
        <?php if(isset($_SESSION["entana"])) { ?>
        <?php for ($i=0; $i <count($_SESSION["entana"]) ; $i++) { ?>
        <div class="fono">
            <img src=" <?php echo $vokatra[$_SESSION["entana"][$i]]['image']; ?>"  width=200 height=200 alt="Aiza ze eo lele">
            <h3><?php echo $vokatra[$_SESSION["entana"][$i]]['nom']; ?></h3>
            <h2><?php echo $vokatra[$_SESSION["entana"][$i]]['prix']; ?></h2>
        </div>
        <?php } ?>
        <?php } ?>
    </div>

    <?php if(!isset($_SESSION["entana"])) { ?>
    <?php echo "PANIER VIDE"; ?>
    <?php } ?>
    <a href="destroypan.php">
        <h1>REMOVE ALL</h1>
    </a>
</body>

</html>