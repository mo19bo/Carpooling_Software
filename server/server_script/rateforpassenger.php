<?php



include("connection.php");

//$jsonarr = '[{"pusername":"hc","aspassenger":3},{"pusername":"test","aspassenger":3}]';
$jsonarr = $_POST['json'];
$objarr = json_decode($jsonarr, true);
$countarr = count ($objarr);
for ($i= 0;$i<$countarr;$i++)
     {
    	$ttt=$objarr[$i]['pusername'];
    	$sql1 = "select aspassenger,aspassengernum from user where username='$ttt'";
    	$query=mysql_query($sql1);
    	$s=mysql_fetch_assoc($query);
    	$rate=$s["aspassenger"];
    	$num=$s["aspassengernum"];
    	$ans=($rate * $num+$objarr[$i]['aspassenger'])/($num+1);
    	$num=$num+1;
    	$sql2="update user set aspassenger='$ans',aspassengernum='$num' where username='$ttt'";
    	mysql_query($sql2);
	}
    
   $array=array("result"=>"success");
    
 echo json_encode($array);   
    
    


?>