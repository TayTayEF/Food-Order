<?php 
	include "connect.php";
	$query = "SELECT * FROM slider";
	$data = mysqli_query($conn, $query);
	$mangSlider = array();
	while ($row = mysqli_fetch_assoc($data)) {
		array_push($mangSlider, new Slider(
			$row['id'],
			$row['link']));
	}
	echo json_encode($mangSlider);
	class Slider{
		 function __construct ($id, $link){
			$this->id 				= $id;
			$this->link 		= $link;
		}
	}
?>