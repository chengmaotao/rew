
			
function trCorlor(){
	var bc='';
	$("tr").mouseover(function(){
		bc = $(this).css("background-color");
		$(this).css("background-color","ac9b93");
	});
	
	$("tr").mouseout(function(){
		$(this).css("background-color",bc);
	});	
}		