<?php
	include "connect.php";
	$tenmon = $_POST['tenmon'];

	$query = "SELECT * FROM mon WHERE mon.tenmon LIKE '%$tenmon%'";
	$data = mysqli_query($conn, $query);
	$result = array();

	while ($row = mysqli_fetch_assoc($data)) {
		$result[] = ($row);
		//code...
	}
	if (!empty($result)) {
		$arr = [
			'success' => true,
			'message' => "Thành công",
			'result' => $result
		];
	} else {
		$arr = [
			'success' => false,
			'message' => "Thất bại!",
			'result' => $result
		];
	}
	print_r(json_encode($arr));
?>