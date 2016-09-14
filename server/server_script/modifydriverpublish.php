<?php


include("connection.php");
/*$json = '{
	"weeks7":0,
	"map_end":"",
	"publish_date":"2014-11-04",
	"location":"",
	"weeks2":0,
	"weeks1":1,
	"weeks6":0,
	"weeks5":0,
	"weeks4":0,
	"weeks3":0,
	"end_date":"2014-11-12",
	"username":"hc",
	"thetotalprice":1,
	"server_num":1,
	"start_time":"01:03",
	"map_begin":"",
	"brief":""
    "pid"=1;
}';*/
$json = $_POST['json'];
$obj = json_decode($json, true);
$name = $obj["username"];
$server_num= $obj["server_num"];
$start_time = $obj["start_time"];
$weeks1 = $obj["weeks1"];
$weeks2 = $obj["weeks2"];
$weeks3 = $obj["weeks3"];
$weeks4 = $obj["weeks4"];
$weeks5 = $obj["weeks5"];
$weeks6 = $obj["weeks6"];
$weeks7 = $obj["weeks7"];
$publish_date = $obj["publish_date"];
$end_date = $obj["end_date"];
$thetotalprice = $obj["thetotalprice"];
$brief = $obj["brief"];
$location = $obj["location"];
$map_begin = $obj["map_begin"];
$map_end = $obj["map_end"];
$pid = $obj["pid"];



    $sql = "update driver  set  username='$name', server_num='$server_num', start_time='$start_time', weeks1='$weeks1', weeks2='$weeks2', weeks3='$weeks3', weeks4='$weeks4', weeks5='$weeks5', weeks6='$weeks6', weeks7='$weeks7', 
                   publish_date='$publish_date', end_date='$end_date', thetotalprice='$thetotalprice', brief='$brief', location='$location', map_begin='$map_begin', map_end='$map_end' where pid='$pid '";
 $te = mysql_query($sql);
if ($te)
    $array=array("result"=>"success");
    
    
echo json_encode($array);
?>