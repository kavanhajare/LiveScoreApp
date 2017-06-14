<?php
 
 
// array for JSON response
$response = array();
 
// include db connect class
require_once __DIR__ . '/db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();
 
// check for post data
/*
if (isset($_GET["ImageId"])) {
    $ImageId = $_GET['ImageId'];
*/ 
    // get a product from products table
  
  $result = mysql_query("SELECT * FROM College_Images") or die(mysql_error());
 
        if (mysql_num_rows($result) > 0) {
 
     $response["product"] = array();
        
	//   $result = mysql_fetch_array($result);
 
while($row = mysql_fetch_array($result)){ 
 $product = array();
         //   $product["pid"] = $result["pid"];
            $product["Image"] = $result["Image"];
            $product["CollegeName"] = $result["CollegeName"];
            $product["CollegeAddress"] = $result["CollegeAddress"];
            $product["CollegeFees"] = $result["CollegeFees"];
            $product["CollegePhNo"] = $result["CollegePhNo"];
			$product["CollegeMailAddress"] = $result["CollegeMailAddress"];
			$product["CollegeWebsite"] = $result["CollegeWebsite"];
			$product["CollegeType"] = $result["CollegeType"];
			$product["CollegeBranches"] = $result["CollegeBranches"];
			$product["CollegeBranchSeats"] = $result["CollegeBranchSeats"];
			array_push($response["product"], $product);
		}
			
	// success
            $response["success"] = 1;
      
            // echoing JSON response
            echo json_encode($response);
        } 
		else {
            // no product found
            $response["success"] = 0;
            $response["message"] = "No product found";
 
            // echo no users JSON
            echo json_encode($response);
        }
    
?>