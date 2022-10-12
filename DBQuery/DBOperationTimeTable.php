<?php
	class DBOperation {
		private $con;
		function __construct(){
			require_once dirname(__FILE__).'/DBConnect.php';  
			
			$db= new DBConnect();
		
			$this->con = $db->connect();
			
		}
		
		
		
		public function mainUpdate($day,$timeFrom,$timeTo,$dep,$batch,$subject,$date,$place,$lecturer){
			
			$r1=$this->update24 ($day,$timeFrom,$timeTo,$dep,$batch,$subject,$date,$place);
			$r2=$this->updateNonAcdmc ($day,$timeFrom,$timeTo,$subject,$date,$place);
			$r3=$this->updateLectcurer ($day,$timeFrom,$timeTo,$subject,$date,$place,$lecturer);

			//return $r1;
			if($r1==$r2){
				return $r1;
			}else{
				return 2;
			}
		}
		
		
		
		/*CRUD -> C -> Create*/
		public function update ($day,$timeFrom,$timeTo,$dep,$batch,$subject,$date){
			if($timeFrom!=12){
				if ($timeTo>$timeFrom){
					$table=$dep.$batch;
					$resetTime=$date." ".$timeTo;
					$stmt_t=$this->con->prepare("CREATE TABLE IF NOT EXISTS `temp_$table` as (select * from $table);");
					$stmt_t->execute();
					/* $object=new DBOperation();
					$object-> */
					$this->eventSchedule($table);
					
					if (($timeTo-$timeFrom)==1){
						$stmt = $this->con->prepare("UPDATE `$table` SET `$timeFrom-$timeTo`='$subject',`reset_date`='$resetTime' WHERE day='$day';");
					}else if (($timeTo-$timeFrom)==2){
						$timeMid=number_format($timeFrom+01.00,2);
						if ($timeMid<10){
							$timeMid="0".$timeMid;
						}
						$stmt = $this->con->prepare("UPDATE `$table` SET `$timeFrom-$timeMid`='$subject',`$timeMid-$timeTo`='$subject',`reset_date`='$resetTime' WHERE day='$day';");
						//echo $timeMid;
					}else if (($timeTo-$timeFrom)==3){
						$timeMid1=number_format($timeFrom+1.00,2);
						if ($timeMid1<10){
							$timeMid1="0".$timeMid1;
						}
						$timeMid2=number_format($timeFrom+2.00,2);
						if ($timeMid2<10){
							$timeMid2="0".$timeMid2;
						}
						$stmt = $this->con->prepare("UPDATE `$table` SET `$timeFrom-$timeMid1`='$subject',`$timeMid1-$timeMid2`='$subject',`$timeMid2-$timeTo`='$subject',`reset_date`='$resetTime' WHERE day='$day';");
					}
					//return $stmt->execute();
					
					if ($stmt->execute()){
						return 1;
					}else{
						return 2;
					}
				}else {
					return 3;
				}
			
			}else {
				return 4;
			}
		}
		
		/* if ($stmt->execute()){
				return true;
			}else{
				return false;
			} */
		
		public function eventSchedule ($table){

			$qury="create event if not exists `event_$table` on schedule every 1 hour starts date_format(now(),'%y-%m-%d- %H:00:00') do
			update $table inner join `temp_$table` on $table.day=`temp_$table`.day 
			set
			$table.`08.00-09.00`=`temp_$table`.`08.00-09.00`,
			$table.`09.00-10.00`=`temp_$table`.`09.00-10.00`,
			$table.`10.00-11.00`=`temp_$table`.`10.00-11.00`,
			$table.`11.00-12.00`=`temp_$table`.`11.00-12.00`,
			$table.`12.00-13.00`=`temp_$table`.`12.00-13.00`,
			$table.`13.00-14.00`=`temp_$table`.`13.00-14.00`,
			$table.`14.00-15.00`=`temp_$table`.`14.00-15.00`,
			$table.`15.00-16.00`=`temp_$table`.`15.00-16.00`,
			$table.`16.00-17.00`=`temp_$table`.`16.00-17.00`,
			$table.`17.00-18.00`=`temp_$table`.`17.00-18.00`,
			$table.reset_date=`temp_$table`.reset_date where $table.reset_date = CONVERT_TZ(now(),'+00:00','+05:30');";
			//$table.reset_date=`temp_$table`.reset_date where $table.reset_date = date_format(now(),'%Y-%m-%d- %H:%i:%s');";
			
			//$qury="create event if not exists `event_ICT1617` on schedule every 1 hour starts date_format(now(),'%y-%m-%d- %h:00:00') do update ICT1617 inner join `temp_ICT1617` on ICT1617.day=`temp_ICT1617`.day set ICT1617.`08.00-09.00`=`temp_ICT1617`.`08.00-09.00`, ICT1617.`09.00-10.00`=`temp_ICT1617`.`09.00-10.00`, ICT1617.`10.00-11.00`=`temp_ICT1617`.`10.00-11.00`, ICT1617.`11.00-12.00`=`temp_ICT1617`.`11.00-12.00`, ICT1617.`12.00-01.00`=`temp_ICT1617`.`12.00-01.00`, ICT1617.`01.00-02.00`=`temp_ICT1617`.`01.00-02.00`, ICT1617.`02.00-03.00`=`temp_ICT1617`.`02.00-03.00`, ICT1617.`03.00-04.00`=`temp_ICT1617`.`03.00-04.00`, ICT1617.`04.00-05.00`=`temp_ICT1617`.`04.00-05.00`, ICT1617.`05.00-06.00`=`temp_ICT1617`.`05.00-06.00`, ICT1617.reset_date=`temp_ICT1617`.reset_date where ICT1617.reset_date = date_format(now(),'%Y-%m-%d- %h:%i:%s');";
			
			/* $event = $this->con-> */mysqli_query($this->con,$qury);
			//$event->execute();
		}
		
		/* public function eventSchedule2 ($table){
			$qury="create event if not exists `event_updater_$table` on schedule every 1 hour starts date_format(now(),'%y-%m-%d- %H:00:00') do
				update $table set $time=$subject where $day=$day";
				
				update (select table_name from time_table_updater_$table where reset_date=date_format(now(),'%y-%m-%d- %H:00:00'))
				set (select time from time_table_updater_$table where reset_date=date_format(now(),'%y-%m-%d- %H:00:00'))=
				(select old_subject from time_table_updater_$table where reset_date=date_format(now(),'%y-%m-%d- %H:00:00'))
				where day=(select day from time_table_updater_$table where reset_date=date_format(now(),'%y-%m-%d- %H:00:00'));
				
				
				update (select table_name from time_table_updater_$table where reset_date=date_format(now(),'%y-%m-%d- %H:00:00');)
				set `08.00-09.00` =
				(select old_subject from time_table_updater_ICT1617 where reset_date=date_format(now(),'%y-%m-%d- %H:00:00'))
				where day=(select day from time_table_updater_ICT1617 where reset_date=date_format(now(),'%y-%m-%d- %H:00:00'));
				
				
				update ict1617 set (select time from time_table_updater_ICT1617 where reset_date=date_format(now(),'%y-%m-%d- %H:00:00')) = (select old_subject from time_table_updater_ICT1617 where reset_date=date_format(now(),'%y-%m-%d- %H:00:00'))
				where day=(select day from time_table_updater_ICT1617 where reset_date=date_format(now(),'%y-%m-%d- %H:00:00'));
				
				update ict1617 set `08.00-09.00` = (select old_subject from time_table_updater_ICT1617 where reset_date=date_format(now(),'%y-%m-%d- %H:00:00'))
				where day=(select day from time_table_updater_ICT1617 where reset_date=date_format(now(),'%y-%m-%d- %H:00:00'));
				
				update ict1617 set `08.00-09.00` = "hsdsja"
				where day=(select day from time_table_updater_ICT1617 where reset_date=date_format(now(),'%y-%m-%d- %H:00:00'));

			$qury2="create event if not exists `event_updater2` on schedule every 1 hour starts date_format(now(),'%y-%m-%d- %H:00:00') do
			update $table set $time=$subject,$time=$subject where $day=$day";
			
			select 

			$qury3="create event if not exists `event_updater3` on schedule every 1 hour starts date_format(now(),'%y-%m-%d- %H:00:00') do
			update $table set $time=$subject,$time=$subject,$time=$subject where $day=$day";
		} */

		
		public function eventScheduleNonAcdmc ($table){
			
			if ($table=="Monday"||$table=="Tuesday"||$table=="Wednesday"||$table=="Thursday"||$table=="Friday"){
				$qury="create event if not exists `event_$table` on schedule every 1 hour starts date_format(now(),'%y-%m-%d- %H:00:00') do
				update $table inner join `temp_$table` on $table.place=`temp_$table`.place 
				set
				$table.`08.00-09.00`=`temp_$table`.`08.00-09.00`,
				$table.`09.00-10.00`=`temp_$table`.`09.00-10.00`,
				$table.`10.00-11.00`=`temp_$table`.`10.00-11.00`,
				$table.`11.00-12.00`=`temp_$table`.`11.00-12.00`,
				$table.`12.00-13.00`=`temp_$table`.`12.00-13.00`,
				$table.`13.00-14.00`=`temp_$table`.`13.00-14.00`,
				$table.`14.00-15.00`=`temp_$table`.`14.00-15.00`,
				$table.`15.00-16.00`=`temp_$table`.`15.00-16.00`,
				$table.`16.00-17.00`=`temp_$table`.`16.00-17.00`,
				$table.`17.00-18.00`=`temp_$table`.`17.00-18.00`,
				$table.reset_date=`temp_$table`.reset_date where $table.reset_date = CONVERT_TZ(now(),'+00:00','+05:30');";
			}else{
				return 6;
				/* $qury="create event if not exists `event_$table` on schedule every 1 hour starts date_format(now(),'%y-%m-%d- %H:00:00') do
				update $table inner join `temp_$table` on $table.day=`temp_$table`.day 
				set
				$table.`08.00-09.00`=`temp_$table`.`08.00-09.00`,
				$table.`09.00-10.00`=`temp_$table`.`09.00-10.00`,
				$table.`10.00-11.00`=`temp_$table`.`10.00-11.00`,
				$table.`11.00-12.00`=`temp_$table`.`11.00-12.00`,
				$table.`12.00-13.00`=`temp_$table`.`12.00-13.00`,
				$table.`13.00-14.00`=`temp_$table`.`13.00-14.00`,
				$table.`14.00-15.00`=`temp_$table`.`14.00-15.00`,
				$table.`15.00-16.00`=`temp_$table`.`15.00-16.00`,
				$table.`16.00-17.00`=`temp_$table`.`16.00-17.00`,
				$table.`17.00-18.00`=`temp_$table`.`17.00-18.00`,
				$table.reset_date=`temp_$table`.reset_date where $table.reset_date = now();"; */
			}

			
			//$table.reset_date=`temp_$table`.reset_date where $table.reset_date = date_format(now(),'%Y-%m-%d- %H:%i:%s');";
			
			//$qury="create event if not exists `event_ICT1617` on schedule every 1 hour starts date_format(now(),'%y-%m-%d- %h:00:00') do update ICT1617 inner join `temp_ICT1617` on ICT1617.day=`temp_ICT1617`.day set ICT1617.`08.00-09.00`=`temp_ICT1617`.`08.00-09.00`, ICT1617.`09.00-10.00`=`temp_ICT1617`.`09.00-10.00`, ICT1617.`10.00-11.00`=`temp_ICT1617`.`10.00-11.00`, ICT1617.`11.00-12.00`=`temp_ICT1617`.`11.00-12.00`, ICT1617.`12.00-01.00`=`temp_ICT1617`.`12.00-01.00`, ICT1617.`01.00-02.00`=`temp_ICT1617`.`01.00-02.00`, ICT1617.`02.00-03.00`=`temp_ICT1617`.`02.00-03.00`, ICT1617.`03.00-04.00`=`temp_ICT1617`.`03.00-04.00`, ICT1617.`04.00-05.00`=`temp_ICT1617`.`04.00-05.00`, ICT1617.`05.00-06.00`=`temp_ICT1617`.`05.00-06.00`, ICT1617.reset_date=`temp_ICT1617`.reset_date where ICT1617.reset_date = date_format(now(),'%Y-%m-%d- %h:%i:%s');";
			
			/* $event = $this->con-> */mysqli_query($this->con,$qury);
			//$event->execute();
		}
		
		
		public function eventScheduleLecturer($table){

			$qury="create event if not exists `event_$table` on schedule every 1 hour starts date_format(now(),'%y-%m-%d- %H:00:00') do
			update $table inner join `temp_$table` on $table.day=`temp_$table`.day 
			set
			$table.`08.00-09.00`=`temp_$table`.`08.00-09.00`,
			$table.`09.00-10.00`=`temp_$table`.`09.00-10.00`,
			$table.`10.00-11.00`=`temp_$table`.`10.00-11.00`,
			$table.`11.00-12.00`=`temp_$table`.`11.00-12.00`,
			$table.`12.00-13.00`=`temp_$table`.`12.00-13.00`,
			$table.`13.00-14.00`=`temp_$table`.`13.00-14.00`,
			$table.`14.00-15.00`=`temp_$table`.`14.00-15.00`,
			$table.`15.00-16.00`=`temp_$table`.`15.00-16.00`,
			$table.`16.00-17.00`=`temp_$table`.`16.00-17.00`,
			$table.`17.00-18.00`=`temp_$table`.`17.00-18.00`,
			$table.reset_date=`temp_$table`.reset_date where $table.reset_date = CONVERT_TZ(now(),'+00:00','+05:30');";
			
			mysqli_query($this->con,$qury);

		}
		
		
		
		public function update24 ($day,$timeFrom,$timeTo,$dep,$batch,$subject,$date,$place){
			$subject=$subject." ".$place;
			if($day=="Sunday" || $day=="Saturday"){
				return 6;
			}else if($timeFrom<=7 || $timeTo>=19){
					return 5;
				}else if($timeFrom!=12){
						if ($timeTo>$timeFrom){
							$table=$dep.$batch;
							$resetTime=$date." ".$timeTo;
							$stmt_t=$this->con->prepare("CREATE TABLE IF NOT EXISTS `temp_$table` as (select * from $table);");
							$stmt_t->execute();
							/* $object=new DBOperation();
							$object-> */
							$this->eventSchedule($table);
							


							if (($timeTo-$timeFrom)==1){
								$stmt = $this->con->prepare("UPDATE `$table` SET `$timeFrom-$timeTo`='$subject',`reset_date`='$resetTime' WHERE day='$day';");
								$stmt_t_create=$this->con->prepare("CREATE TABLE IF NOT EXISTS `time_table_updater_$table` (number int AUTO_INCREMENT primary key,table_name varchar(10),old_subject varchar(70),place varchar(50),time varchar(20),day varchar(20),reset_date varchar(30));");
								$stmt_t_create->execute(); /** this statement is Mandatory before bind param 
								if not shown error like bellow
								Fatal error: Uncaught Error: Call to a member function bind_param() on boolean in C:\wamp64\www\LectureSS\DBQuery\DBOperationTimeTable.php on line 178
								*/
								
								
								$stmt_t_insertt=$this->con->prepare("INSERT INTO `time_table_updater` (`table_name`,`time`,`day`,`reset_date`,`old_subject`) VALUES (?,?,?,?,?);");
								$time=$timeFrom."-".$timeTo;
								$stmt_t_insertt->bind_param("sssss",$table,$time,$day,$resetTime,$place); 

								
								
								
								/* $t = "select * from `ict1617`;";
								$tt=$con->query($t);*/
								
								//$qry="INSERT INTO `time_table_updater` (`table_name`,`time`,`day`,`reset_date`,`old_subject`) VALUES ('table','time','day','resetTime','ttt');";
								
								$q=$this->con->prepare("select `$time` from `$table` where day='$day';");
								$q->execute();// generate fatal error when Activating a prepare code ($this->con->prepare) after execute() 
								$q->store_result();
								

								$sql = "select `$time` from `$table` where day='monday'";
								$result = mysqli_query($this->con,$sql);
								
								$subject ="";
								if($result){
									if(mysqli_num_rows($result)>0){
										$subject = mysqli_fetch_assoc($result);
										$subject = $subject[$time];
									}
								}else{
									$subject = mysqli_error($this->con);
								}
								
								$stmt_t_insert=$this->con->prepare("INSERT INTO `time_table_updater_$table` (`table_name`,`time`,`day`,`reset_date`,`old_subject`) VALUES ('$table','$time','$day','$resetTime','$subject');");
							
								
								//$qry2=$this->con->prepare("INSERT INTO `time_table_updater` (`table_name`,`time`,`day`,`reset_date`,`old_subject`) SELECT ('table','time','day','resetTime', `08.00-09.00` from 'ict1617' where day='monday');");
								//$qry2->execute();
								//$con->mysql_query($qry);
								//******$ss="select `$time` from `$table` where day=$day;";
								//$result= mysql_query($ss);
								//******$result= mysqli_query($con,$ss);
								//$result2=mysql_result($result);
								//******$result2=mysql_result($result);
								
							}
							else if (($timeTo-$timeFrom)==2){
								$timeMid=number_format($timeFrom+01.00,2);
								if ($timeMid<10){
									$timeMid="0".$timeMid;
								}
								$stmt = $this->con->prepare("UPDATE `$table` SET `$timeFrom-$timeMid`='$subject',`$timeMid-$timeTo`='$subject',`reset_date`='$resetTime' WHERE day='$day';");
								$stmt_t_create=$this->con->prepare("CREATE TABLE IF NOT EXISTS `time_table_updater2` (number int AUTO_INCREMENT primary key,table_name varchar(10),old_subject varchar(70),place varchar(50),time1 varchar(20),time2 varchar(20),day varchar(20),reset_date varchar(30));");
								$stmt_t_create->execute();
								$stmt_t_insert=$this->con->prepare("insert into `time_table_updater2` (`table_name`,`time1`,`time2`,`day`,`reset_date`) values (?,?,?,?,?);");
								//$stmt_t_insert=$this->con->prepare("insert into `time_table_updater2` (`time1`,`time2`,`day`,`reset_date`) values ('?','?','?','?');");
								$time1=$timeFrom."-".$timeMid;
								$time2=$timeMid."-".$timeTo;
								/*************************
								*     cannot bind values if table is not already created.
								*     in here cannot create table inside if condition bellow
								*     therefore in this case "$stmt_t_create" should run before bind the param
								**************************/
								$stmt_t_insert->bind_param("sssss",$table,$time1,$time2,$day,$resetTime);
								//echo $timeMid;
							}else if (($timeTo-$timeFrom)==3){
								$timeMid1=number_format($timeFrom+1.00,2);
								if ($timeMid1<10){
									$timeMid1="0".$timeMid1;
								}
								$timeMid2=number_format($timeFrom+2.00,2);
								if ($timeMid2<10){
									$timeMid2="0".$timeMid2;
								}
								$stmt = $this->con->prepare("UPDATE `$table` SET `$timeFrom-$timeMid1`='$subject',`$timeMid1-$timeMid2`='$subject',`$timeMid2-$timeTo`='$subject',`reset_date`='$resetTime' WHERE day='$day';");
								$stmt_t_create=$this->con->prepare("CREATE TABLE IF NOT EXISTS `time_table_updater3` (number int AUTO_INCREMENT primary key,table_name varchar(10),old_subject varchar(70),place varchar(50),time1 varchar(20),time2 varchar(20),time3 varchar(20),day varchar(20),reset_date varchar(30));");
								$stmt_t_create->execute();
								$stmt_t_insert=$this->con->prepare("insert into `time_table_updater3` (`table_name`,`time1`,`time2`,`time3`,`day`,`reset_date`) values (?,?,?,?,?,?);");
								$time1=$timeFrom."-".$timeMid1;
								$time2=$timeMid1."-".$timeMid2;
								$time3=$timeMid2."-".$timeTo;
								$stmt_t_insert->bind_param("ssssss",$table,$time1,$time2,$time3,$day,$resetTime);
							}
							
							//return $stmt->execute();
							
							//return $subject;
							if ($stmt->execute() && $stmt_t_insert->execute() ){
								
								return 1;
							}else{
								return 2;
							}
						}else {
							return 3;
						}
					
					}else {
						return 4;
					}
			
		}
		
		
		public function updateLectcurer ($day,$timeFrom,$timeTo,$subject,$date,$place,$lecturer){
			$subject=$subject." ".$place;
			if($day=="Sunday" || $day=="Saturday"){
				return 6;
			}else if($timeFrom<=7 || $timeTo>=19){
					return 5;
				}else if($timeFrom!=12){
						if ($timeTo>$timeFrom){
							$table=$lecturer;
							$resetTime=$date." ".$timeTo;
							$stmt_t=$this->con->prepare("CREATE TABLE IF NOT EXISTS `temp_$table` as (select * from $table);");
							$stmt_t->execute();
							/* $object=new DBOperation();
							$object-> */
							$this->eventScheduleLecturer($table);
							


							if (($timeTo-$timeFrom)==1){
								$stmt = $this->con->prepare("UPDATE `$table` SET `$timeFrom-$timeTo`='$subject',`reset_date`='$resetTime' WHERE day='$day';");
							}else if (($timeTo-$timeFrom)==2){
								$timeMid=number_format($timeFrom+01.00,2);
								if ($timeMid<10){
									$timeMid="0".$timeMid;
								}
								$stmt = $this->con->prepare("UPDATE `$table` SET `$timeFrom-$timeMid`='$subject',`$timeMid-$timeTo`='$subject',`reset_date`='$resetTime' WHERE day='$day';");
								//echo $timeMid;
							}else if (($timeTo-$timeFrom)==3){
								$timeMid1=number_format($timeFrom+1.00,2);
								if ($timeMid1<10){
									$timeMid1="0".$timeMid1;
								}
								$timeMid2=number_format($timeFrom+2.00,2);
								if ($timeMid2<10){
									$timeMid2="0".$timeMid2;
								}
								$stmt = $this->con->prepare("UPDATE `$table` SET `$timeFrom-$timeMid1`='$subject',`$timeMid1-$timeMid2`='$subject',`$timeMid2-$timeTo`='$subject',`reset_date`='$resetTime' WHERE day='$day';");
							}
							//return $stmt->execute();
							
							if ($stmt->execute()){
								return 1;
							}else{
								return 2;
							}
						}else {
							return 3;
						}
					
					}else {
						return 4;
					}
			
		}
		
		
		public function updateNonAcdmc ($day,$timeFrom,$timeTo,$subject,$date,$place){
			if($day=="Sunday" || $day=="Saturday"){
				return 6;
			}else if($timeFrom<=7 || $timeTo>=19){
					return 5;
				}else if($timeFrom!=12){
						if ($timeTo>$timeFrom){
							$table=$day;
							$resetTime=$date." ".$timeTo;
							$stmt_t=$this->con->prepare("CREATE TABLE IF NOT EXISTS `temp_$table` as (select * from $table);");
							$stmt_t->execute();
							/* $object=new DBOperation();
							$object-> */
							$this->eventScheduleNonAcdmc($table);
							
							if (($timeTo-$timeFrom)==1){
								$stmt = $this->con->prepare("UPDATE `$table` SET `$timeFrom-$timeTo`='$subject',`reset_date`='$resetTime' WHERE place='$place';");
							}else if (($timeTo-$timeFrom)==2){
								$timeMid=number_format($timeFrom+01.00,2);
								if ($timeMid<10){
									$timeMid="0".$timeMid;
								}
								$stmt = $this->con->prepare("UPDATE `$table` SET `$timeFrom-$timeMid`='$subject',`$timeMid-$timeTo`='$subject',`reset_date`='$resetTime' WHERE place='$place';");
								//echo $timeMid;
							}else if (($timeTo-$timeFrom)==3){
								$timeMid1=number_format($timeFrom+1.00,2);
								if ($timeMid1<10){
									$timeMid1="0".$timeMid1;
								}
								$timeMid2=number_format($timeFrom+2.00,2);
								if ($timeMid2<10){
									$timeMid2="0".$timeMid2;
								}
								$stmt = $this->con->prepare("UPDATE `$table` SET `$timeFrom-$timeMid1`='$subject',`$timeMid1-$timeMid2`='$subject',`$timeMid2-$timeTo`='$subject',`reset_date`='$resetTime' WHERE place='$place';");
							}
							//return $stmt->execute();
							
							if ($stmt->execute()){
								return 1;
							}else{
								return 2;
							}
						}else {
							return 3;
						}
					
					}else {
						return 4;
					}
			
		}
		
		
		
		
		
	}
	/* 
create event update_table on schedule every 8 minute starts current_timestamp do
update ict1617 inner join  ict1617temp on ict1617.day=ict1617temp.day set ict1617.`8.00-9.00`=ict1617temp.`8.00-9.00` where ict1617.reset_date!=ict1617temp.reset_date;


alter table ict1617 rename column `8.00-9.00` `8:00-9:00` varchar (20); 
*/
?>