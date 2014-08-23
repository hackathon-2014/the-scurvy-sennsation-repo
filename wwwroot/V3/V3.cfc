component {
header name='Access-Control-Allow-Origin' value='*';

remote function Claim(GoogleID,TreasureID) returnformat='json' {
	include '/Inc/newQuery.cfm'
	sql = '
	DECLARE @GoogleID UNIQUEIDENTIFIER = ?
	DECLARE @TreasureID Int = ?
	
	UPDATE Treasure
	SET Claimed = getdate()
	WHERE TreasureID = @TreasureID
	AND Treasure_UsrID = (
		SELECT PersonID
		FROM Person
		WHERE GoogleID = @GoogleID
	)

	SELECT TreasureID
	,Convert(Varchar,Claimed,127) AS Claimed
	FROM TreasureView
	WHERE TreasureID = @TreasureID
	AND UsrGoogleID = @GoogleID
	'
	params = [
		 GoogleID
		,TreasureID
	]
	include '/Inc/queryExecute.cfm'
	return result
}
}