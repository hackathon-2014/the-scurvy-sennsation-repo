component {

remote function WhereLocationGUID(GoogleID,LocationGUID) returnformat='json' {
	include '/Inc/newQuery.cfm'
	sql = '
	SELECT GoogleID,BootyName,LocationName
	FROM TreasureView
	WHERE UsrGoogleID = ?
	'
	params = [
		 GoogleID
	]
	if (IsDefined('arguments.LocationGUID')) {
		sql &= '	AND LocationGUID = ?'
		params = [
			 GoogleID
			,LocationGUID
		]
	}
	include '/Inc/queryExecute.cfm'
	return result
}
}