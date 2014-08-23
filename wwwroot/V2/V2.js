dom.GoogleID = $('[name=GoogleID]')
dom.UsrGoogleID = $('[name=UsrGoogleID]')
dom.LocationGUID = $('[name=LocationGUID]')
dom.BootyName = $('[name=BootyName]')

;(function() {
	$('[name=Save').on('click',Save)
	function Save() {
		var local = {}

		local.type			= 'POST'
		local.dataType		= 'json'
		local.url			= 'V1.cfc'
		local.data			= {}
		local.data.method	= 'Save'
		local.data.GoogleID = dom.GoogleID.val()
		local.data.UsrGoogleID= dom.UsrGoogleID.val()
		local.data.LocationGUID = '39BDB862-AECC-4D48-8766-7522FD9F3922'
		local.data.BootyName = 'Phil+Weiser' // dom.BootyName.val()
		local.Promise = $.ajax(local)
		local.Promise.done(done)
		local.Promise.fail(fail)
	}
	function done(response,status,xhr) {
		var local = {}

		debugger
	}
})()
