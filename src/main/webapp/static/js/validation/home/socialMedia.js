$(document).ready(function() {
	$('.twitter').click(function() {
		$('#ex2').jqm({ajax : 'showTwitterPage.html', modal: true,trigger : 'a.ex2trigger' });
	});
	$('.facebook').click(function() {
		//alert("Hello ::::::	Face Book");
		//$('#ex2').jqm({ajax : 'showTwitterPage.html', modal: true,trigger : 'a.ex2trigger' });
	});
	$('.linkedin').click(function() {
		//alert("Hello ::::::	Linked In");
		//$('#ex2').jqm({ajax : 'showTwitterPage.html', modal: true,trigger : 'a.ex2trigger' });
	});	
});