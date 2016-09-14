<?php

//待改

include("connection.php");

$json = '{"username":"test","asdriverrate":3}';
//$json = $_POST['json'];
$obj = json_decode($json, true);
$username = $obj["username"];

$checksql = "select asdriverrate,asdriverratenum from user where username='$username'";
$checkquery1 = mysql_query($checksql);
$check = mysql_fetch_assoc($checkquery1);
$price = $check["asdriverrate"];
$num=$check["asdriverratenum"];
$ans=($price*$num+$obj["asdriverrate"])/($num+1);
$num=$num+1;
$sql="update user set asdriverrate='$ans',asdriverratenum='$num' where username='$username'";
mysql_query($sql);


?>