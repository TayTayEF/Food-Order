<?php 
	include "connect.php";
	$sdt = $_POST['sdt'];
	$query = "SELECT * FROM taikhoan WHERE sdt = '$sdt'";

	$data = mysqli_query($conn, $query);
	$count = mysqli_num_rows($data);
	if($count>0){
		echo "Success";
	} 
	else{
		echo "Fail";
	}
?>