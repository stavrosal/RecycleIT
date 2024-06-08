<?php
    // Database connection details
    $host = "localhost";
    $uname = "root";
    $pass = "";
    $dbname = "users";  

    // Connect to the database
    $conn = mysqli_connect($host, $uname, $pass, $dbname);

    // Check connection
    if (!$conn) {
        die("Connection failed: " . mysqli_connect_error());
    }

    // Prepare the SQL query
    $isAdmin = 0; //to get users where isAdmin is 0
    $sql = "SELECT username FROM users WHERE isAdmin = ?"; 

    // Initialize the prepared statement
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("i", $isAdmin);

    // Execute the statement
    $stmt->execute();

    // Get the result
    $result = $stmt->get_result();
    $data = [];

    // Fetch data
    if ($result->num_rows > 0) { 
        while ($row = $result->fetch_assoc()) {
            $data[] = $row["username"];

        }
    } else { 
        $data = ["message" => "0 results"];
    } 

    // Return data as JSON
    echo json_encode($data);

    // Close the connection
    mysqli_close($conn);
?>