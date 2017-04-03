<?php
$msg=$_POST['param1'];
$myfile = fopen("data.txt", "w") or die("Unable to open file!");
fwrite($myfile, $msg);
fclose($myfile);
?>