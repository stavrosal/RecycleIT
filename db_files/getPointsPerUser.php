<?php
    // connect to db
    $host="localhost";
    $uname="root";
    $pass="";
    $dbname="users";  

    // get data from request
    $user = $_GET["username"];

    $conn = mysqli_connect($host,$uname,$pass) or die("cannot connect");
    mysqli_select_db($conn, $dbname);

    $sql = "SELECT points FROM " . $dbname . " WHERE username = '" . $user . "'";

    $result = mysqli_query($conn, $sql);

    if (mysqli_num_rows($result) > 0) {
        while($row = $result->fetch_assoc()) {
            $points = $row['points'];
        }
    }

    // Return data as JSON
    echo json_encode(["points" => $points]);

    // Close connection
    mysqli_close($conn);
?>
