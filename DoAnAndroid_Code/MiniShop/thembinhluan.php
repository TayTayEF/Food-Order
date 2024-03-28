<?php
	include "connect.php";
	$sdt = $_POST['sdt'];
	$noidung= $_POST['noidung'];
	$mamon = $_POST['mamon'];

	$query = "INSERT INTO binhluan(id,mamon,sdt,noidung) VALUES (null,'$mamon','$sdt','$noidung') ";
	if(mysqli_query($conn,$query)) {
		echo "1";
	}else{
		echo "0";
	}

?>