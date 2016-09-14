<?php



include("connection.php");

$json = '{"username":"test"}';
//$json = $_POST['json'];
$obj = json_decode($json, true);
$pusername = $obj["username"];

$checksql = "select count(*) as sum from myorder where pusername ='pusername' and isarr=1 and isgos=1 and israte=0";
$checkquery1 = mysql_query($checksql);
$check = mysql_fetch_assoc($checkquery1);
$count = $check["sum"];
if($count == 0){	
     $array[] = $check;
}else{
    $sql = "select username from myorder,driver where pusername ='pusername' and isarr=1 and isgos=1 and israte=0";
    $query = mysql_query($sql);
    while ($result = mysql_fetch_assoc($query)) {
        $array[] = array_merge($result, $check);
    }
   
}

echo json_encode($array);

?>