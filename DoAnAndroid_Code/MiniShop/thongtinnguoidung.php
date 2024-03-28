<?php 
	include "connect.php";
	$sdt = $_POST['sdt'];
	$query = "SELECT * FROM taikhoan WHERE taikhoan.sdt = '$sdt'";
	$data = mysqli_query($conn, $query);
	$mangSlider = array();
	while ($row = mysqli_fetch_assoc($data)) {
		array_push($mangSlider, new Slider(
			$row['id'],
			$row['tenkhachhang'],
			$row['sdt'],
			$row['email']));
	}
	echo json_encode($mangSlider);
	class Slider{
		 function __construct ($id, $tenkhachhang, $sdt, $email){
			$this->id 				= $id;
			$this->tenkhachhang 		= $tenkhachhang;
			$this->sdt		= $sdt;
			$this->email 		= $email;
		}
	}
?>