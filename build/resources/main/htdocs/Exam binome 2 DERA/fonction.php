<?php
    function take_categorie_fruit($vokatra){
        $p=0;
        for ($i=0; $i < count($vokatra); $i++) { 
            if ($vokatra[$i]['categorie']=='fruits') {
                $valiny[$p]=$vokatra[$i];
                $p++;
            }
        }
        return $valiny;
    }
    function take_categorie_legumes($vokatra){
        $p=0;
        for ($i=0; $i < count($vokatra); $i++) { 
            if ($vokatra[$i]['categorie']=='legumes') {
                $valiny[$p]=$vokatra[$i];
                $p++;
            }
        }
        return $valiny;
    }
    function take_categorie_beverage($vokatra){
        $p=0;
        for ($i=0; $i < count($vokatra); $i++) { 
            if ($vokatra[$i]['categorie']=='beverage') {
                $valiny[$p]=$vokatra[$i];
                $p++;
            }
        }
        return $valiny;
    }
?>  