<?php
	include "connect.php";
	$tenkhachhang = $_POST['tenkhachhang'];
	$diachi= $_POST['diachi'];
	$sodienthoai = $_POST['sodienthoai'];
	$tongtien = $_POST['tongtien'];
	$ghichu = $_POST['ghichu'];

	$query = "INSERT INTO donhang(id,tenkhachhang,sodienthoai,tongtien,ghichu,diachi) VALUES (null,'$tenkhachhang','$sodienthoai','$tongtien','$ghichu','$diachi') ";
	if(mysqli_query($conn,$query)) {
		$id = $conn -> insert_id;
		echo $id;
	}else{
		echo "Thất bại!";
	}

?>