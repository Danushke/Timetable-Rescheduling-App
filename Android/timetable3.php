<?php

# define is constat in php to use store simple values it can't be change durind the script
#syntax is constant
#define(name,value,case-sensitive)

	//require"login.php";
	$user_namee="Danushka";
	define('DB_HOST','localhost');
	define('DB_USER','root');
	define('DB_PASS','Root123');
	define('DB_NAME','lecture_scheduling');
	
	$con=new mysqli(DB_HOST,DB_USER,DB_PASS,DB_NAME);
	if(mysqli_connect_errno()){
		die('Unable to connect database'.mysqli_connect_errno()); // "dei" for stop further execution
	}
	//$stmt=$con->prepare("select index_no,short_name,reg_no,dep,mobile from user order by index_no;");
	
	/* $stmt = $this->con->prepare("SELECT * FROM ict1617sem1 "); // WHERE index_no = ?"
	$stmt->bind_param("s",$index_no);
	$stmt->execute();
	return $stmt->get_result()->fetch_assoc();
	 */
	 
	 
	 
	/* $stmt = $con->prepare("SELECT * FROM ict1617sem1 ");
	$stmt->execute();
	$stmt->bind_result(); */

	
	
///	$id=("select index_no from user where username like '$user_namee';");
///	$re=mysql_query($id);
///	$value=mysql_fetch_object($re);
//	$_SESSION['myindex']=$value;
$data=array();
if ($_SERVER['REQUEST_METHOD']=='POST'){
	
	if(isset($_POST["timetable"])){
	
		$index=$_POST["timetable"];

		
		$stmt = $con->prepare("select * from $index where day='monday'");
		//$stmt->bind_param("s",$day);
		$stmt->execute();
		$stmt->bind_result($t0,$t1,$t2,$t3,$t4,$t5,$t6,$t7,$t8,$t9,$t10,$t11); 
		
		
		//$data=array(); 
		//fetch the data from database
		while($stmt->fetch()){
			$temp=array();
			/* $data['indexno']=$index_no;
			$data['shortname']=$short_name;
			$data['reg_no']=$reg_no;
			$data['mobile']=$mobile;
			$data['username']=$username;
			$data['password']=$password; */
		
			$temp['day']=$t0;
			$temp['t1']=$t1;
			$temp['t2']=$t2;
			$temp['t3']=$t3;
			$temp['t4']=$t4;
			$temp['t5']=$t5;
			$temp['t6']=$t6;
			$temp['t7']=$t7;
			$temp['t8']=$t8;
			$temp['t9']=$t9;
			$temp['t10']=$t10;
			
			/* $temp['ICT1301']=$index_no;
			$temp['ICT1302']=$short_name;
			$temp['ICT1303']=$reg_no;
			$temp['CMT1301']=$dep;
			$temp['CMT1303']=$mobile; */
			
			
			array_push($data,$temp);
		}
	
	}else{
		$data['error']=true;
		$data['message']="Required Fields are Missing";
	}
}else{
	$data['error']=true;
	$data['message']="Invalid Requset";
}

echo json_encode($data);
	
?>