<?php



header("Content-Type:text/html;charset=utf8");
$link = mysql_connect(SAE_MYSQL_HOST_M.':'.SAE_MYSQL_PORT,SAE_MYSQL_USER,SAE_MYSQL_PASS);
mysql_set_charset('utf8', $link);
$db = mysql_select_db(SAE_MYSQL_DB);
if (!$db) {
    echo ('Could not connect to MySQL:' . mysql_error());
}

?>