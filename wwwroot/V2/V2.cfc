component {
remote function WhereGoogleID(GoogleID) returnformat='json' {
	include '/Inc/newQuery.cfm'
	// I'm returning the GoogleID of the person who left the booty here.
	// I'm accepting the GoogleID of the person picking up the booty here.
	sql = '
	SELECT GoogleID,BootyName,LocationGUID
	,Convert(Varchar,Claimed,127) AS Claimed
	,TreasureID
	FROM TreasureView
	WHERE UsrGoogleID = ?
	'
	params = [
		 GoogleID
	]
	include '/Inc/queryExecute.cfm'
	return result
}

}