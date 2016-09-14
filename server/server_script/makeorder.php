<?php

//在搜索后选择乘坐的路线

include("connection.php");

//$json = '{"pid":"2","username":"test"}';
$json = $_POST['json'];
$obj = json_decode($json, true);
$pid = $obj["pid"];
$username=$obj["username"];

$checksql = "select count(*) as sum from myorder where pid ='$pid' and pusername='$username'" ;
$checkquery = mysql_query($checksql);
$check = mysql_fetch_assoc($checkquery);
$count = $check["sum"];

if($count != 0){	
    $array = array('result'=>'exist');
}else{
    $sql = "insert into myorder(pid,pusername) values('$pid','$username')";
    mysql_query($sql);
    $array = array('result'=>'success');
   
}

echo json_encode($array);

?>