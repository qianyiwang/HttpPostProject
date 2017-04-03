<?php
$msg=$_POST['param1'];
$myfile = fopen("data.txt", "r+") or die("Unable to open file!");
$data = fgets($myfile);
// fwrite($myfile, "none");
file_put_contents('data.txt', "none");
fclose($myfile);
echo $data;
?>