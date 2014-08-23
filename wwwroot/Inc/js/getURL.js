function getURL(arg) {
	var local = {}
	var result = ''
	
	local.hashes = location.href.slice(
		location.href.indexOf('?') + 1
	)
	local.hashes = local.hashes.split('&')
	for(var i = 0; i < local.hashes.length; i++)	{
		local.url = local.hashes[i].split('=')
		if (local.url[0].toUpperCase() === arg.toUpperCase()) {
			result = local.url[1]
		}
	}
	return result
}