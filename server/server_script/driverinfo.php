<?php


include("connection.php");

//$json = '{"username":"test1"}';
$json = $_POST['json'];
$obj = json_decode($json, true);
$username = $obj["username"];


$checksql = "select * from user where username='$username'";
$checkquery1 = mysql_query($checksql);
$check = mysql_fetch_assoc($checkquery1);


echo json_encode($check);

?>