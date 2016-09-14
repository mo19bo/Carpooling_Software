<?php

include("connection.php");

$json = $_POST['json'];
date_default_timezone_set("PRC");
$array=array("result"=>"success","curtime"=>date("Y-m-d H:i:s"));

echo json_encode($array);
?>