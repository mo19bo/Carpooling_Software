<?php



include("connection.php");

//$json = '{"pid":"1"}';
$json = $_POST['json'];
$obj = json_decode($json, true);
$pid = $obj["pid"];
$usql = "update driver set isgo = 0 where pid='$pid'";
//mysql_query($usql);

$checksql = "select count(*) as sum from myorder where pid ='$pid' and isarr=1 and ispgo=1";
$checkquery1 = mysql_query($checksql);
$check = mysql_fetch_assoc($checkquery1);
$count = $check["sum"];
if($count == 0){	
     $array[] = $check;
}else{
    $sql = "select * from myorder where myorder.pid='$pid and isarr=1 and ispgo=1'";
    $query = mysql_query($sql);
    while ($result = mysql_fetch_assoc($query)) {
        $array[] = array_merge($result, $check);
    }
   
}

echo json_encode($array);

?>