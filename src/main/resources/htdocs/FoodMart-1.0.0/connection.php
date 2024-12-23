<?php
try {
    // Créer une connexion PDO
    $pdo = new PDO('mysql:host=localhost;dbname=base_de_produits;charset=utf8', 'nom_utilisateur', 'mot_de_passe');
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Création de la table produits
    $sql = "CREATE TABLE IF NOT EXISTS produits (
        id INT AUTO_INCREMENT PRIMARY KEY,
        nom VARCHAR(255) NOT NULL,
        prix DECIMAL(10, 2) NOT NULL,
        unite VARCHAR(50),
        note DECIMAL(2, 1),
        image_url VARCHAR(255),
        reduction INT DEFAULT 0,
        description TEXT
    )";

    $pdo->exec($sql);
    echo "La table 'produits' a été créée avec succès.";
} catch (PDOException $e) {
    echo "Erreur : " . $e->getMessage();
}
?>
