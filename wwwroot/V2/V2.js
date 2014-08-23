;(function() {
	var local = {}

	local.dataType		= 'json'
	local.url			= 'V2.cfc'
	local.data			= {}
	local.data.method	= 'WhereGoogleIDx'
	local.data.GoogleID = 'E409F57C-106A-4E70-8DE7-85AC90FC60AE'
	local.Promise = $.ajax(local)
	local.Promise.done(done)
	local.Promise.fail(fail)
	function done(response,status,xhr) {
		var local = {}

		debugger
	}
})()
