<?php

	require_once '../DBQuery/DBOperationTimeTable.php';
	//associative array
	$response = array();
	//to storing data we use the php post method_exists
	//check if there is post method or not
	//"$_SERVER" is predefind variable in php
	if($_SERVER['REQUEST_METHOD']=='POST'){
		//check if user has given all required values
		//no need order only thing is match with tabble head
		
		$db=new DBOperation();
		$result=$db->update24($_POST['day'],$_POST['timeFrom'],$_POST['timeTo'],$_POST['dep'],$_POST['batch'],$_POST['sub'],$_POST['date'],$_POST['place']);
		
		if ($result==1){
			$response['error']=false;
			$response['message']="Update Successful";
		}else if($result==2){
			$response['error']=true;
			$response['message']="Update Unsuccessful\nSome Error Occured";
		}else if($result==3){
			$response['error']=true;
			$response['message']="Entered Time Invalid";
		}else if($result==4){
			$response['error']=true;
			$response['message']="Cannot Start the Lecture At 12.00";
		}else if($result==5){
			$response['error']=true;
			$response['message']="Error you can add the lecture \nfrom 8:00 AM to 6:00 PM";
		}else if($result==6){
			$response['error']=true;
			$response['message']="Error you can add the lectures\nweekdays only";
		}
	}else{
		$response['error']=true;
		$response['message']="Invalid Requset";
	}
	
	echo json_encode($response);
?>




