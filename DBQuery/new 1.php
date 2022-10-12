<?php
$dep="abcd";$batch="efgh";
$tablee=$dep.$batch;
echo $tablee."<br>";
ECHO $tablee."<br>";
$tabl;
$tabl=2;
EchO $tabl."<br>";

$table="ICT1617";



/* 
$qury="create event if not exists `event_$table` on schedule every 1 hour starts date_format(now(),'%y-%m-%d- %h:00:00') do
			update $table inner join `temp_$table` on $table.day=`temp_$table`.day 
			set
			$table.`08.00-09.00`=`temp_$table`.`08.00-09.00`,
			$table.`09.00-10.00`=`temp_$table`.`09.00-10.00`,
			$table.`10.00-11.00`=`temp_$table`.`10.00-11.00`,
			$table.`11.00-12.00`=`temp_$table`.`11.00-12.00`,
			$table.`12.00-01.00`=`temp_$table`.`12.00-01.00`,
			$table.`01.00-02.00`=`temp_$table`.`01.00-02.00`,
			$table.`02.00-03.00`=`temp_$table`.`02.00-03.00`,
			$table.`03.00-04.00`=`temp_$table`.`03.00-04.00`,
			$table.`04.00-05.00`=`temp_$table`.`04.00-05.00`,
			$table.`05.00-06.00`=`temp_$table`.`05.00-06.00`,
			$table.reset_date=`temp_$table`.reset_date where $table.reset_date = date_format(now(),'%Y-%m-%d- %h:%i:%s');";
			
			echo $qury; */


?>