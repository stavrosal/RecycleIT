<?php
	 $data = array();
	 $user = $_GET["username"];
	 $password = $_GET["password"];
	 $host="localhost";
	 $uname="root";
	 $pass="";
	 $dbname="users";
	 $dbh = mysqli_connect($host,$uname,$pass) or die("cannot connect");
	 mysqli_select_db($dbh, $dbname);
	 
	 $sql = "SELECT username, password, isAdmin FROM users WHERE username =  '" . $user . "' AND password =  '" . $password . "'";

	 //echo $sql;
	 $result = mysqli_query($dbh, $sql);
	 
	 while ($row = mysqli_fetch_array($result)) {
		//printf ("%s (%s)\n", $row["username"], $row["password"]);
		$data[$row['username']] = $row['password'].$row['isAdmin'];
	}
	header("Content-Type: application/json");
	echo json_encode($data);

	 mysqli_close($dbh);
?>

