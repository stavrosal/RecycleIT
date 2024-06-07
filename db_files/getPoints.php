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


    $sql = "SELECT username, points FROM users"; 
    // $sql = "SELECT username, points FROM users ORDER BY points desc LIMIT 3"; 

     $result = mysqli_query($conn, $sql);

     $data = [];

     if ($result->num_rows > 0)  
    { 
        // OUTPUT DATA OF EACH ROW 
        while($row = $result->fetch_assoc()) {
            $data[] = ["username" => $row["username"], "points" => $row["points"]];
        }
        
    }  
    else { 
        echo "0 results"; 
    } 

    // Επιστροφή των δεδομένων ως JSON
    echo json_encode($data);

    // Κλείσιμο της σύνδεσης
    mysqli_close($conn);
?>
