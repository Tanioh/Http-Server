<?php
//$sary = $_GET["photo"];
?>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="style.css">
    <title>SARY</title>
</head>

<body>
    <?php if(isset($_GET['photo'])) { ?>
    <div class="fonosary">
        <div class="sarybe"><img src="<?php echo $_GET['photo'];?>" width="600" alt="aiza ze eo!"></div>
        <div class="retour"><a href="index.php">RETOUR</a></div>
    </div>
    <?php } ?>
</body>

</html>