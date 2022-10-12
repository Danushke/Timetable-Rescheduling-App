<?php

	class DBConnect{
		private $con;
		function __construct(){
		
		}
		function connect(){
			include_once dirname(__FILE__).'/Constant.php'; //import the file
			$this->con = new mysqli(DB_HOST,DB_USER,DB_PASS,DB_NAME);
			
			if(mysqli_connect_errno()){
			//die('Unable to connect database'.mysqli_connect_errno());
				echo "Failed to connect with database".mysqli_connect_err();
			}
			return $this->con;
		}
		
		/* define('DB_HOST','localhost');
		define('DB_USER','root');
		define('DB_PASS','Root123');
		define('DB_NAME','lecture_scheduling');
		
		$con=new mysqli(DB_HOST,DB_USER,DB_PASS,DB_NAME);
		if(mysqli_connect_errno()){
			die('Unable to connect database'.mysqli_connect_errno()); // "dei" for stop further execution
		} */
	}
?>