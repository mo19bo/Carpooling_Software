<?php

//修改isarr,isgo,isgos

include("connection.php");

$jsonarr = '[{"opid":1},{"opid":5}]';
//$json = $_POST['json'];
$objarr = json_decode($jsonarr, true);	
$countarr = count ($objarr);
echo ($countarr);
for ($i= 0;$i<$countarr;$i++)
     {
    		$t=$objarr[$i]['opid'];
         $sql = "update myorder set isarr=1,ispgo=1 where opid='$t'";
         $ans=mysql_query($sql);
    	 $sql1 = "select pid from myorder where opid='$t'";
    	$query=mysql_query($sql1);
    	$re=mysql_fetch_assoc($query);
    	
    	$tt=$re["pid"];
   			echo $tt;
    	$sql2 = "update driver set isgo=1 where pid='$tt'";
    	mysql_query($sql2);
     }

?>