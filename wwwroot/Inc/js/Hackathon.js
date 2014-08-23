var dom = {} // Document Object Model
dom.msg = $('.msg')
dom.main = $('main')
dom.fw = {}
dom.fw.home = $('input[name=fw-home]').val()

dom.failure = function(svc, SQLError) {
	// dom.msg.text(SQLError.message).addClass('label-danger')
	debugger
}

// Global ajax fail handler
function fail(xhr, status, response) {
	// For some reason, Firefox was throwing this error when I'd refresh the page, but
	// it would sail right through the debugger statement AND an alert box, so I'm just
	// going to ignore errors with empty responseTexts.
	if (xhr.responseText) {
		dom.msg.text(status + ': ' + response).addClass('label-warning')
		dom.main.html(xhr.responseText)
	}
	debugger
}

/*
window.onerror = function(myError) {
	console.log('window.onerror')
}
*/
$('a,button').tooltip()

;(function() {
	$('main').removeAttr('hidden')
	// But we may have let's say a table that needs to be hidden:
	$(window).load(loaded) 
	function loaded() {
		$('#showWhileLoading').hide('fast')
		$('#hideWhileLoading').show('fast')
	}
})()
