<?php



include("connection.php");

//$json = '{"opid":"2"}';
$json = $_POST['json'];
$obj = json_decode($json, true);
$opid = $obj["opid"];

$sql = "delete from myorder where opid='$opid'" ;
$array=array("result"=>mysql_query($sql));



echo json_encode($array);

?>