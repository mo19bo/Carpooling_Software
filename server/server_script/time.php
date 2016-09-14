<?php

include("connection.php");



while (0)
{
    $sql="update driver set thetotalprice=thetotalprice+1 where username='hc'";
    
    mysql_query($sql);
	sleep(3);
}

date_default_timezone_set("PRC");
echo date("Y-m-d H:i:s");
?>