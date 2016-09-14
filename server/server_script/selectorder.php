<?php

//列出搜索后的路线

include("connection.php");

//$json = '{"map_end":"333","username":"test1"}';
$json = $_POST['json'];
$obj = json_decode($json, true);
$map_end = $obj["map_end"];
$username=$obj["username"];
$checksql = "select count(*) as sum from driver where map_end ='$map_end' and username<>'$username'";
$checkquery = mysql_query($checksql);
$check = mysql_fetch_assoc($checkquery);
$count = $check["sum"];

if($count == 0){	
    $array[] = $count;
}else{
    $sql = "select * from driver where map_end='$map_end'";
    $query = mysql_query($sql);
    while ($result = mysql_fetch_assoc($query)) {
        $array[] = array_merge($result, $check);
    }
   
}

echo json_encode($array);

?>