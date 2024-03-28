<?php
	include "connect.php";
	$query = "SELECT * FROM `cuahang`";
	$mangCuaHang = array();
	$data 			= mysqli_query($conn, $query);
	while ($row = mysqli_fetch_assoc($data)) {
		array_push($mangCuaHang, new CuaHang(
			$row['id'],
			$row['diachi']
		));
	}
	echo json_encode($mangCuaHang);
	class CuaHang{
		function __construct ($id, $diachi){
			$this->id 			=$id;
			$this->diachi		=$diachi;
		}
	}
?>