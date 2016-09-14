<?php



include("connection.php");

//$json = '{"username":"test1"}';
$json = $_POST['json'];
$obj = json_decode($json, true);
$username = $obj["username"];
$sql = "select count(pid) as sum from driver where username='$username' and isgo=1";
$query = mysql_query($sql);
$check = mysql_fetch_array($query);
$count = $check["sum"];
if ($count==0){
    $array=array("result"=>"go");
}
else {
    $array=array("result"=>"arrive");
    $sql = "select pid from driver where username='$username' and isgo=1";
	$query = mysql_query($sql);
	$result = mysql_fetch_array($query);
    $array=array("result"=>"arrive","pid"=>$result["pid"]);
}


echo json_encode($array);

?>