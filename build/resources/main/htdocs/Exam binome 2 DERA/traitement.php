<?php
session_start();
include("data.php");
include("fonction.php");
$_SESSION["select"]=$_POST["categorie"];
if ($_SESSION["select"]==1) {
    $_SESSION["filter"]=take_categorie_fruit($vokatra);
    $_SESSION["select"]='fruits';
    header('Location:index.php');
}

if ($_SESSION["select"]==2) {
    $_SESSION["filter"]=take_categorie_legumes($vokatra);
    $_SESSION["select"]='Légumes';
    header('Location:index.php');
}

if ($_SESSION["select"]==3) {
    $_SESSION["filter"]=take_categorie_beverage($vokatra);
    $_SESSION["select"]='beverage';
    header('Location:index.php');
}
header('Location:index.php');
?>