<?php



include("connection.php");

//$json = '{"password":"","username":""}';
$json = $_POST['json'];
$obj = json_decode($json, true);
$loginname = $obj["username"];
$password = $obj["password"];

$sql = "select * from user where username ='$loginname' or telephone='$loginname'";
$query = mysql_query($sql);
$result = mysql_fetch_assoc($query);

if($password != $result["password"] or $password ==""){
    $array = array('result'=>'failed', 'username'=>'', 'phonenumber'=>'', 'sex'=>0, 'introduction'=>'', 'driverlicense'=>'','idcard'=>'','isdriver'=>0,'iscustomer'=>0);
}else{
    $array = array('result'=>'success', 'username'=>$result["username"], 'phonenumber'=>$result["phonenumber"], 'sex'=>$result["sex"], 'introduction'=>$result["introduction"], 'driverlicense'=>$result["driverlicense"], 'idcard'=>$result["idcard"], 'isdriver'=>$result["isdriver"], 'iscustomer'=>$result["iscustomer"],
                  	'aspassenger'=>$result["aspassenger"],'asdriverrate'=>$result["asdriverrate"],'job'=>$result["job"]);
}

echo json_encode($array);

?>