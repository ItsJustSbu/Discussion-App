<?php

$username= "s2452375";
$password= "s2452375";
$database= "d2452375";
$link= mysqli_connect("127.0.0.1", $username, $password, $database);

if ( mysqli_connect_errno() ) {
	// If there is an error with the connection, stop the script and display the error.
	exit('Failed to connect to database: ' . mysqli_connect_error());
}

$output= array();

if($r=mysqli_query($link, "SELECT RESPONSE from RESPONSES"))
{
	while($row=$r->fetch_assoc())
	{
		$output[]=$row;
	}
}

mysqli_close($link);
echo json_encode($output);

?>