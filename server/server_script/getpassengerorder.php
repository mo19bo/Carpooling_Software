<?php



include("connection.php");

//$json = '{"username":"hc"}';
$json = $_POST['json'];
$obj = json_decode($json, true);
$loginname = $obj["username"];

$checksql = "select count(*) as sum from myorder where pusername ='$loginname'";
$checkquery1 = mysql_query($checksql);
$check = mysql_fetch_assoc($checkquery1);
$count = $check["sum"];
if($count == 0){	
     $array[] = $check;
}else{
    $sql = "select * from myorder,driver where myorder.pusername='$loginname' and myorder.pid=driver.pid";
    $query = mysql_query($sql);
    while ($result = mysql_fetch_assoc($query)) {
        $array[] = array_merge($result, $check);
    }
   
}

echo json_encode($array);

?>