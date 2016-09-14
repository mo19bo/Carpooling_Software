<?php



include("connection.php");

//$json = '{"opid":"2"}';
$json = $_POST['json'];
$obj = json_decode($json, true);
$pid = $obj["pid"];

$sql = "delete from driver where pid='$pid'" ;
$array=array("result"=>mysql_query($sql));



echo json_encode($array);

?>