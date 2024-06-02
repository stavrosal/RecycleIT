<?php
	 $data = array();
	 $user = $_GET["username"];
	 $password = $_GET["password"];
	 $isAdmin = $_GET["isAdmin"];
	 $points = $_GET["points"];
	 $host="localhost";
	 $uname="root";
	 $pass="";
	 $dbname="users";
	 $dbh = mysqli_connect($host,$uname,$pass) or die("cannot connect");
	 mysqli_select_db($dbh, $dbname);
	 
	 $exists = "SELECT COUNT(username) AS count FROM " . $dbname . " WHERE username = '" . $user . "'";
	 $sql = "INSERT INTO " . $dbname . " VALUES ('" . $user . "' , '" . $password . "' , '" . $isAdmin . "' , '" .  $points . " ')";
	 
	 
	 //Check if account, already exists and return -1 if it does
	 $doesExist = mysqli_query($dbh, $exists);
	 $existResult = mysqli_fetch_array($doesExist);
	 $count = (int) $existResult['count'];
	 
	 if ($count != 0){
		echo -1;
	 }
	 else{
		$result = mysqli_query($dbh, $sql);
		echo 1;
	 }
	 
	 mysqli_close($dbh);
?>

