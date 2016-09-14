<?php


include("connection.php");
//$json = '{"username":"test1", "password":"test", "phonenumber":"18328588447","sex":0,"introduction":"","driverlicense":"","idcard":"","isdriver":0,"iscustomer":0}';
$json = $_POST['json'];
$obj = json_decode($json, true);
$name = $obj["username"];
$psd = $obj["password"];
$phonenumber = $obj["phonenumber"];
$sex = $obj["sex"];
$introduction = $obj["introduction"];
$driverlicense = $obj["driverlicense"];
$idcard = $obj["idcard"];
$isdriver = $obj["isdriver"];
$iscustomer = $obj["iscustomer"];


$checksql = "select count(*) as sum from user where username ='$name'";
$checkquery = mysql_query($checksql);
$check = mysql_fetch_array($checkquery);
$count = $check["sum"];

if($count == 1){
    $array = array('result'=>'exist', 'username'=>'', 'phonenumber'=>'', 'sex'=>0, 'introduction'=>'', 'driverlicense'=>'','idcard'=>'','isdriver'=>0,'iscustomer'=>0);
}else if($count == 0){
    $sql = "insert into user(username, password, phonenumber,sex,introduction,driverlicense,idcard,isdriver,iscustomer) values('$name', '$psd', '$phonenumber','$sex','$introduction','$driverlicense','$idcard','$isdriver','$iscustomer')";
    mysql_query($sql);
    
    $sql = "select * from user where username ='$name'";
	$query = mysql_query($sql);
	$result = mysql_fetch_assoc($query);
    $array = array('result'=>'success', 'username'=>$result["username"], 'phonenumber'=>$result["phonenumber"], 'sex'=>$result["sex"], 'introduction'=>$result["introduction"], 'driverlicense'=>$result["driverlicense"], 'idcard'=>$result["idcard"], 'isdriver'=>$result["isdriver"], 'iscustomer'=>$result["iscustomer"]);
}else{
    $array = array('result'=>'failed', 'username'=>'', 'phonenumber'=>'', 'sex'=>0, 'introduction'=>'', 'driverlicense'=>'','idcard'=>'','isdriver'=>0,'iscustomer'=>0);
}
echo json_encode($array);
?>