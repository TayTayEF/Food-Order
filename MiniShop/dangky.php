<?php 
	include "connect.php";
	$tenkhachhang = $_POST['tenkhachhang'];
	$sdt	= $_POST['sdt'];
	$email = $_POST['email'];
	$matkhau	= $_POST['matkhau'];

	$query = "INSERT INTO taikhoan (id, tenkhachhang, sdt, email, matkhau) VALUES (null, '$tenkhachhang','$sdt','$email','$matkhau')";
	$data = mysqli_query($conn, $query);
	if($data){
		echo "Done";
	} 
	else{
		echo "Error";
	}
?>