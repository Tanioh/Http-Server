<?php
try {
    // Connexion à la base de données
    $pdo = new PDO('mysql:host=localhost;dbname=base_de_produits;charset=utf8', 'nom_utilisateur', 'mot_de_passe');
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Préparation de l'insertion
    $sql = "INSERT INTO produits (nom, prix, unite, note, image_url, reduction, description) VALUES
        ('Banane Fraîche', 18.00, '1 unité', 4.5, 'images/banane.png', 30, 'Bananes fraîches de qualité supérieure.'),
        ('Jus de Melon Sunstar', 18.00, '1 unité', 4.5, 'images/jus_melon.png', 0, 'Jus de melon naturel sans sucre ajouté.'),
        ('Concombre Bio', 18.00, '1 unité', 4.5, 'images/concombre.png', 0, 'Concombres bio frais et croquants.'),
        ('Lait Demi-écrémé', 18.00, '1 unité', 4.5, 'images/lait.png', 0, 'Lait demi-écrémé, parfait pour toute la famille.'),
        ('Bonbons Acidulés', 18.00, '1 unité', 4.5, 'images/bonbons.png', 30, 'Bonbons acidulés avec un goût intense.'),
        
        -- Autres produits similaires
        ('Pomme Rouge Bio', 15.00, '1 kg', 4.7, 'images/pomme_rouge.png', 10, 'Pommes rouges bio, douces et croquantes.'),
        ('Orange Bio', 20.00, '1 kg', 4.6, 'images/orange.png', 15, 'Oranges bio juteuses et riches en vitamine C.'),
        ('Poire William', 12.00, '1 kg', 4.4, 'images/poire.png', 0, 'Poires William délicieuses et sucrées.'),
        ('Tomates Cerises', 10.00, '250 g', 4.8, 'images/tomates_cerises.png', 5, 'Tomates cerises bio, idéales pour les salades.'),
        ('Eau Minérale', 1.50, '1 litre', 4.3, 'images/eau.png', 0, 'Eau minérale naturelle de source.'),
        ('Chocolat Noir 70%', 25.00, '100 g', 4.9, 'images/chocolat_noir.png', 20, 'Chocolat noir riche et intense à 70% de cacao.'),
        ('Yaourt Nature', 8.00, '1 unité', 4.2, 'images/yaourt.png', 0, 'Yaourt nature riche en probiotiques.'),
        ('Pain Complet', 5.00, '1 unité', 4.6, 'images/pain_complet.png', 0, 'Pain complet bio, source de fibres.'),
        ('Œufs de Poules Élevées en Plein Air', 6.00, '6 unités', 4.7, 'images/oeufs.png', 10, 'Œufs frais de poules élevées en plein air.'),
        ('Fromage Comté Affiné', 30.00, '200 g', 4.8, 'images/fromage_comte.png', 15, 'Fromage Comté affiné 18 mois, goût unique.')
    ";

    // Exécuter l'insertion
    $pdo->exec($sql);
    echo "Les produits ont été insérés avec succès dans la base de données.";
} catch (PDOException $e) {
    echo "Erreur : " . $e->getMessage();
}
?>
