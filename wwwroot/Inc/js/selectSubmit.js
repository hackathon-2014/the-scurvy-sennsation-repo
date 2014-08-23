(function() {
	$(document).on('change','select',changed)
	
	function changed() {
		$(this).closest('form').submit()
	}
})()
