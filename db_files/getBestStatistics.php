<?php
$data = array();
$host = "localhost";
$uname = "root";
$pass = "";
$dbname = "users";

$conn = new mysqli($host, $uname, $pass, $dbname);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// Query to get the top 3 users with the highest points in paper, glass, metal, and plastic
$query = "
    SELECT username, points
    FROM users
    ORDER BY points DESC
    LIMIT 3
";

$result = $conn->query($query);

if ($result) {
    $topUsers = array();
    while ($row = $result->fetch_assoc()) {
        $topUsers[] = array(
            'username' => $row['username'],
            'points' => $row['points']
        );
    }

    try {
        echo json_encode(
			$topUsers,
         JSON_THROW_ON_ERROR);
    } catch (JsonException $e) {
        die($e);
    }

} else {
    echo json_encode(array('error' => 'Unable to fetch top users'));
}

$conn->close();
?>