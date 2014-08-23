<cfscript>
if (request.fw.try) {
	try {
		result = queryExecute(sql,params)
	} catch(any Exception) {
		result	= Exception.Detail
		request.fw.msg	= Exception.Detail
		request.fw.mod = 'label-success'
	}
} else {
	result = queryExecute(sql,params)
}
</cfscript>
