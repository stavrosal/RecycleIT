<?php
// Σύνδεση με τη βάση δεδομένων
$host = "localhost";
$uname = "root";
$pass = "";
$dbname = "users";  

// Λήψη δεδομένων από το αίτημα
$user = $_GET["username"];
$current_paper = $_GET["current_paper"];
$current_glass = $_GET["current_glass"];
$current_metal = $_GET["current_metal"];
$current_plastic = $_GET["current_plastic"];

// Σύνδεση με τη βάση δεδομένων
$conn = new mysqli($host, $uname, $pass, $dbname);

// Έλεγχος σύνδεσης
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// Προετοιμασμένη δήλωση για την ενημέρωση των δεδομένων
$sql = "UPDATE users SET paper = (paper + ?), glass = (glass + ?), metal = (metal + ?), plastic = (plastic + ?) WHERE username = ?";

$stmt = $conn->prepare($sql);
if ($stmt === false) {
    die("Prepare failed: " . $conn->error);
}

// Δέσμευση των παραμέτρων
$stmt->bind_param("iiiis", $current_paper, $current_glass, $current_metal, $current_plastic, $user);

// Εκτέλεση της δήλωσης
if ($stmt->execute()) {
    echo json_encode(["status" => 1]);
} else {
    echo json_encode(["status" => -1, "error" => $stmt->error]);
}

// Κλείσιμο της δήλωσης και της σύνδεσης
$stmt->close();
$conn->close();
?>
