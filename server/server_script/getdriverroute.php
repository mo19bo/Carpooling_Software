<?php



include("connection.php");

//$json = '{"username":"test1"}';
$json = $_POST['json'];
$obj = json_decode($json, true);
$loginname = $obj["username"];

$checksql = "select count(*) as sum from driver where username ='$loginname'";
$checkquery = mysql_query($checksql);
$check = mysql_fetch_assoc($checkquery);
$count = $check["sum"];

if($count == 0){	
    $array[] = $check;
}else{
    $sql = "select * from driver where username='$loginname'";
    $query = mysql_query($sql);
    while ($result = mysql_fetch_assoc($query)) {
        $array[] = array_merge($result, $check);
    }
   
}

echo json_encode($array);

?>