<?php
    // connect to db
    $host="localhost";
    $uname="root";
    $pass="";
    $dbname="users";  

    // get data from request
    $data = array();
    $user = $_GET["username"];
    $points = $_GET["points"]; 

    $conn = mysqli_connect($host,$uname,$pass) or die("cannot connect");
     mysqli_select_db($conn, $dbname);

$sql = "UPDATE users SET points = (points + " . $points . ") WHERE username = '" . $user . "'";


$stmt = $conn->prepare($sql);

if ($stmt->execute()) {
    echo json_encode(["status" => 1]);
} else {
    echo json_encode(["status" => -1]);
}

$stmt->close();
$conn->close();
?>
