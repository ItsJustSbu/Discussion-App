<?php

$database_username= "s2452375";
$database_password= "s2452375";
$database= "d2452375";
$link= mysqli_connect("127.0.0.1", $database_username, $database_password, $database);

if ( mysqli_connect_errno() ) {
	// If there is an error with the connection, stop the script and display the error.
	exit('Failed to connect to database: ' . mysqli_connect_error());
}

$author=$_POST['author'];
$title=$_POST['title'];
$question=$_POST['question']
$vote = 0;
$Sql_Insert_Query="INSERT INTO USERS(TITLE,QUESTION,VOTES,AUTHOR) SELECT '$title','$question','$vote','$author'";
$sql_update_Query="UPDATE QUESTIONS SET AUTHOR_ID =(SELECT USER_ID FROM USERS WHERE USERS.USERNAME ='$author'"
if (mysqli_query($link,$Sql_Insert_Query) and mysqli_query($link,$sql_update_Query) ){
    echo 'Registration Successful';
}
else{
    echo 'Error! Something went wrong';
}
?>