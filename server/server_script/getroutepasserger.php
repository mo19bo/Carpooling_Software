<?php



include("connection.php");

$json = '{"pid":"1"}';
//$json = $_POST['json'];
$obj = json_decode($json, true);
$pid = $obj["pid"];

$checksql = "select count(*) as sum from myorder where pid ='$pid'";
$checkquery1 = mysql_query($checksql);
$check = mysql_fetch_assoc($checkquery1);
$count = $check["sum"];
if($count == 0){	
     $array[] = $check;
}else{
    $sql = "select * from myorder where myorder.pid='$pid'";
    $query = mysql_query($sql);
    while ($result = mysql_fetch_assoc($query)) {
        $array[] = array_merge($result, $check);
    }
   
}

echo json_encode($array);

?>