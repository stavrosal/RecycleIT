<?php
	 $data = array();
	 $user = $_GET["username"];
	 $password = $_GET["password"];
	 $isAdmin = $_GET["isAdmin"];
	 $points = $_GET["points"];
	 $paper = $_GET["paper"];
	 $glass = $_GET["glass"];
	 $metal = $_GET["metal"];
	 $plastic = $_GET["plastic"];
	 $current_paper = $_GET["current_paper"];
	 $current_glass = $_GET["current_glass"];
	 $current_metal = $_GET["current_metal"];
	 $current_plastic = $_GET["current_plastic"];
	 $host="localhost";
	 $uname="root";
	 $pass="";
	 $dbname="users";
	 $dbh = mysqli_connect($host,$uname,$pass) or die("cannot connect");
	 mysqli_select_db($dbh, $dbname);
	 //SELECT COUNT(username) AS count FROM users WHERE username = '' AND password = ''
	 
	 $exists = "SELECT COUNT(username) AS count FROM " . $dbname . " WHERE username = '" . $user . "'";
	 $sql = "INSERT INTO " . $dbname . " VALUES ('" . $user . "' , '" . $password . "' , '" . $isAdmin . "' , '" .  $points . " ' , '" .  $paper . " ' , '" .  $glass . " ' , '" .  $metal . " ' , '" .  $plastic . " ' ,  '" .  $current_paper . " ' , '" .  $current_glass . " ' , '" .  $current_metal . " ' , '" .  $current_plastic . " ')";
	 
	 //echo $exists;
	 //echo $sql;
	 
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

