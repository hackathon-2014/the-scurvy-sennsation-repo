dom.GoogleID = $('[name=GoogleID]')
dom.UsrGoogleID = $('[name=UsrGoogleID]')
dom.LocationName = $('[name=LocationName]')
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
		local.data.LocationGUID = '02E0476C-8117-455A-8388-04DAD9794B87'
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
