<?php
    // connect to db
    $host="localhost";
    $uname="root";
    $pass="";
    $dbname="users";  

    // get data from request
    $user = $_GET["username"];

    // establish the database connection
    $conn = mysqli_connect($host, $uname, $pass) or die("cannot connect");
    mysqli_select_db($conn, $dbname);

    // SQL query to get the points, paper, metal, glass, and plastic columns for the specified user
    $sql = "SELECT current_paper, current_glass, current_metal, current_plastic FROM " . $dbname . " WHERE username = '" . $user . "'";
    
    // execute the query
    $result = mysqli_query($conn, $sql);

    // check if the user exists in the database and fetch the data
    if (mysqli_num_rows($result) > 0) {
        while($row = $result->fetch_assoc()) {
            $current_paper = $row['current_paper'];
            $current_glass = $row['current_glass'];
            $current_metal = $row['current_metal'];
            $current_plastic = $row['current_plastic'];
        }
    } else {
        echo json_encode(["error" => "User not found"]);
        exit();
    }

    // return the data as JSON
    echo json_encode([
        "current_paper" => $current_paper,
        "current_glass" => $current_glass,
        "current_metal" => $current_metal,
        "current_plastic" => $current_plastic
    ]);

    // close the database connection
    mysqli_close($conn);
?>
