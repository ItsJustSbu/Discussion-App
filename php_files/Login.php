<?php


$database_username= "s2452375";
$database_password= "s2452375";
$database= "d2452375";


$link= mysqli_connect("127.0.0.1", $database_username, $database_password, $database);

if ( mysqli_connect_errno() ) {
	// If there is an error with the connection, stop the script and display the error.
	exit('Failed to connect to database: ' . mysqli_connect_error());
}
$uname=$_POST['username'];
$pword=$_POST['password'];


$output = mysqli_query("SELECT USERNAME,PASSWORD FROM USERS WHERE USERNAME = '$uname' AND PASSWORD = '$pword'");

if(mysqli_num_rows($output) > 0)
echo '1';  // for correct login response
else
echo '0'; // for incorrect login response

mysqli_close($link);

?>