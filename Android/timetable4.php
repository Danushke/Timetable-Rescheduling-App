<?php

//Object oriented Style
	$user_namee="Danushka";
	define('DB_HOST','localhost');
	define('DB_USER','root');
	define('DB_PASS','Root123');
	define('DB_NAME','lecture_scheduling');
	
	$con=new mysqli(DB_HOST,DB_USER,DB_PASS,DB_NAME);
	if($con->connect_errno){
		echo"Failed to Connect my SQL";
		$mysqli->connect_error;
		exit();
	}
	
	//$sqlQuery="select * from ict1617 where day = 'wednesday' ";
	$sqlQuery="select * from ict1617";
	// using fetch_row
	if ($result=$con->query($sqlQuery)){
		while($row=$result->fetch_row()){
			//printf("%s (%s)\n",$row[0],$row[2]);
			//echo ($row[0].$row[2]) ;
			echo json_encode($row);
			
		}
		$result->free_result();
	}
	
	//using fetch_array
	/* if ($result=$con->query($sqlQuery)){
		while($row=$result->fetch_array()){
			array_push($arr=array($row));
			//echo $arr;
			echo json_encode($arr);
		}
		$result->free_result();
	} */
	$con->close();
	
?>