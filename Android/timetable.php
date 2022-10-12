<?php
	
	require_once '../DBQuery/DBOperation.php';
	//associative array
	$response = array();
	if($_SERVER['REQUEST_METHOD']=='POST'){
		
		$dbo=new DBOperation();
		$result=$dbo->getTimeTable($_POST['timetable']);
		$response['error']=false;
		
		//fetch the data from database
		while($stmt->fetch()){
			$temp=array();
			/* $data['indexno']=$index_no;
			$data['shortname']=$short_name;
			$data['reg_no']=$reg_no;
			$data['mobile']=$mobile;
			$data['username']=$username;
			$data['password']=$password; */
			
			
			$temp['t1']=$t1;
			$temp['t2']=$t2;
			$temp['t3']=$t3;
			$temp['t4']=$t4;
			$temp['t5']=$t5;
			$temp['t6']=$t6;
			
			/* $temp['ICT1301']=$index_no;
			$temp['ICT1302']=$short_name;
			$temp['ICT1303']=$reg_no;
			$temp['CMT1301']=$dep;
			$temp['CMT1303']=$mobile; */
			
			
			array_push($response,$temp);
		}
	}
	else{
		$response['error']=true;
		$response['message']="Invalid Requst";
	}
	echo json_encode($response);
?>

