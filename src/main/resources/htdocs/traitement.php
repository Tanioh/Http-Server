<?php
session_start();
echo $_SESSION["gaga"];
// Actual form processing (if method is POST)
// if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // Try to get form data from various sources
    $nom = isset($_POST['nom']) ? htmlspecialchars($_POST['nom']) : 
           (isset($rawInput['nom']) ? htmlspecialchars($rawInput['nom']) : 'N/A');
    
    $age = isset($_POST['age']) ? (int)$_POST['age'] : 
           (isset($rawInput['age']) ? (int)$rawInput['age'] : 'N/A');

    echo "<h2>Données reçues :</h2>";
    echo "<p>Nom : $nom</p>";
    echo "<p>Âge : $age</p>";
// } else {
//     echo "<p>Le formulaire n'a pas été soumis correctement.</p>";
// }
?>