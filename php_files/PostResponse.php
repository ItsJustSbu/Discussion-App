<?php

$database_username= "s2452375";
$database_password= "s2452375";
$database= "d2452375";
$link= mysqli_connect("127.0.0.1", $database_username, $database_password, $database);

if ( mysqli_connect_errno() ) {
	// If there is an error with the connection, stop the script and display the error.
	exit('Failed to connect to database: ' . mysqli_connect_error());
}

$responder=$_POST['responder'];
$title=$_POST['title'];
$response=$_POST['response']
$Sql_Insert_Query="INSERT INTO RESPONSES(TITLE,RESPONSE,VOTES,RESPONSER) SELECT '$title','$response',0,'$responder'";
if (mysqli_query($link,$Sql_Insert_Query) and mysqli_query($link,$sql_update_Query) ){
    echo '0';
}
else{
    echo '1';
}
?>