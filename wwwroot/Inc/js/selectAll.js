(function() {
	$(document).on('click','#selectAll',SelectAll)
	function SelectAll() {
		if ($(this).is(':checked')) {
			$(this).closest('table').find('input:checkbox').prop('checked',true)
		} else {
			$(this).closest('table').find('input:checkbox').removeAttr('checked')
		}
	}
})()
