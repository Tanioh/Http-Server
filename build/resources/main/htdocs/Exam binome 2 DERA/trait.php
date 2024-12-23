<?php
session_start();
include("data.php");
$indice=$_GET["a"];
$_SESSION["entana"][]=$indice;
header('location:index.php');
//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\

?>