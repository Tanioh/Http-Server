<?php
session_start();
include("data.php");
$prodaka = 'PRODUITS';
if (isset($_SESSION["select"])) {
    $prodaka =$_SESSION["select"];
}
if (isset($_SESSION["filter"])) {
    $vokatra =$_SESSION["filter"];
}
?>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="style.css">
    <title>PAGE 1 Noice</title>
</head>

<body>
    <header class="header">
        <a href="destroy.php" class="logo">GROCERY</a>
        <nav class="navbar">
            <a href="#" class="active">Home</a>
            <a href="panier.php">Mon Panier</a>
            <a href="#">Vegetables & Fruits</a>
            <a href="#">Upgrade to pro</a>
            <a href="#">Contact Us</a>
        </nav>
        <div class="contact">
            <p>call to : +231345544564</p>
            <p>email : Deralaza@gmail.com</p>
        </div>
    </header>

    <section class="home">
        <div class="home-content">
            <div class="cathead">
                <div class="fix">
                    <form action="traitement.php" method="post">
                        <select name="categorie" id="select" onchange="this.form.submit()">
                            <option value="0">Catégorie</option>
                            <option value="1">Légume et fruits</option>
                            <option value="2">Lsdg</option>
                            <option value="3">sdf</option>

                        </select>

                    </form>
                </div>
                <div class="headcat">
                    <h2>Categorie:<?php echo $prodaka; ?></h2>
                </div>
            </div>

        </div>

    </section>
    <form action="panier.php" method="post">
        <div class="contain-produit">
            <?php for ($i=0; $i < count($vokatra); $i++) { ?>
            <div class="fono">
                <a href="sary.php?photo=<?php echo $vokatra[$i]['image']; ?>"><img
                        src="<?php echo $vokatra[$i]['image']; ?>" width=200 height=200 alt="GORYRR"></a>
                <h3><?php echo $vokatra[$i]['nom']; ?></h3>
                <h2><?php echo $vokatra[$i]['prix']; ?></h2>
                <a href="trait.php?a=<?php echo $i; ?>">ADD TO CARD</a>
            </div>
            <?php } ?>
        </div>
    </form>
    <section class="home1">
        <div class="home-content1">
            <h1>GROCERY</h1>
            <h3>Predator ULTIME</h3>
            <p>Lorem ipsum dolor sit amet consectetur
                adipisicing elit. Minus aliquid deleniti
                repudiandae praesentium provident quibusdam
                voluptatem nemo quaerat, beatae saepe, officia
                maxime enim minima, ipsam qui. Pariatur.</p>
            <div class="btn-box1">
                <a href="#">Hire me</a>
                <a href="#">Let's Talk</a>
            </div>
        </div>
        <div>
            <div class="align-logo">
                <h1>PAYPAL</h1>
                <h1>LOGO</h1>
                <h1>LOGO</h1>
                <h1>LOGO</h1>
            </div>
            <div>
                <h3>Contact Details</h3>
            </div>
            <div>
                <p>sflqfdge: ergaergaerg</p>
                <p>ezfa,gnaegnm:ajperjpg</p>
                <p>glanernganerlg:a"orhgoar</p>
                <p>aerjgpaerl:gaoerng</p>
                <p>gkanelnglanelanglaerl</p>
                <p>qerjhgonelrga</p>
            </div>
        </div>
    </section>
</body>

</html>