<?php 
	include "connect.php";
	$sdt = $_POST['sdt'];
	$matkhau = $_POST['matkhau'];

	$query = "SELECT * FROM taikhoan WHERE sdt = '$sdt' AND matkhau = '$matkhau'";

	$data = mysqli_query($conn, $query);
	$count = mysqli_num_rows($data);
	if($count>0){
		echo "Success";
	} 
	else{
		echo "Fail";
	}
?>