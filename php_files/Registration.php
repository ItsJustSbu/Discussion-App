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

$Sql_Insert_Query="INSERT INTO USERS(USERNAME,PASSWORD) SELECT '$uname','$pword'";

if (mysqli_query($link,$Sql_Insert_Query)){
    echo 'Registration Successful';
}
else{
    echo 'Error! Something went wrong';
}
?>